package com.ssfw.common.log.manager;

import com.baomidou.mybatisplus.annotation.TableId;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.ssfw.auth.util.LoginUserUtil;
import com.ssfw.common.centext.SpringContextHolder;
import com.ssfw.common.framework.annotation.EntityName;
import com.ssfw.common.framework.annotation.EntityField;
import com.ssfw.common.log.annotation.ActionLogIgnore;
import com.ssfw.common.log.annotation.ActionLogName;
import com.ssfw.common.log.annotation.ActionLogSelector;
import com.ssfw.common.log.constant.ActionTypeEnum;
import com.ssfw.common.log.entity.ActionDetail;
import com.ssfw.common.log.entity.ActionLog;
import com.ssfw.common.log.mapper.ActionDetailMapper;
import com.ssfw.common.log.mapper.ActionLogMapper;
import com.ssfw.common.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import javax.validation.constraints.NotNull;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;


/**
 * 操作日志管理器
 *
 * @author a
 * @date 2016/10/28 10:37
 * Description:在controller直接使用将无法使用spring的事务管理器管理事务
 */
@Slf4j
public class ActionLogManager implements Serializable {


    private static final long serialVersionUID = -1191913281818371869L;
    private final ActionLogMapper actionLogMapper;
    private final ActionDetailMapper actionDetailMapper;

    private static class Handler{
        private static final ActionLogManager INSTANCE = new ActionLogManager();
    }

    public static ActionLogManager getInstance() {
        return Handler.INSTANCE;
    }

    /**
     * 该方法在反序列化时会被调用，该方法不是接口定义的方法，有点儿约定俗成的感觉
     * @return
     * @throws ObjectStreamException
     */
    protected Object readResolve() throws ObjectStreamException {
        return Handler.INSTANCE;
    }
    /**写日志的线程池*/
    private final ExecutorService pool;

    private ActionLogManager() {

        this.actionLogMapper = SpringContextHolder.getBean(ActionLogMapper.class);
        this.actionDetailMapper = SpringContextHolder.getBean(ActionDetailMapper.class);

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("sp-action-log-pool-%d").build();

        pool = new ThreadPoolExecutor(1, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }


    private void persist(@NotNull final ActionLog... actionLogs){

        pool.execute(() -> {
            if (null == actionLogMapper){
                log.error("bean ActionLogMapper is null");
                return;
            }
            int len = actionLogs.length;
            for (ActionLog actionLog : actionLogs) {
                if (++len>1){
                    //主记录
                    actionLog.setMasterId(actionLogs[0].getActionId());
                }
                actionLog.setObjectId(StringUtil.setEmptyToNull(actionLog.getObjectId()+""));
                actionLog.setClientId(StringUtil.setEmptyToNull(actionLog.getClientId()));
                actionLog.setMasterId(NumberUtil.setEmptyToNull(actionLog.getMasterId()));
                actionLog.setOperDate(new Date());
                //开启事务处理
                try {
                    actionLogMapper.insert(actionLog);
                    if (null == actionLog.getDetailList()) {
                        return;
                    }
                    List<ActionDetail> details = actionLog.getDetailList();
                    for (ActionDetail detail : details) {
                        if (StringUtils.isBlank(detail.getFieldName())) {
                            continue;
                        }
                        detail.setActionId(actionLog.getActionId());
                        actionDetailMapper.insert(detail);
                    }
                } catch (Exception e) {
                    log.error("persist action log error,action name:"+actionLog.getActionName(),e);
                }
            }
        });
    }

    /**
     * 记录日志信息
     *
     * @param actionLogs 日志记录
     * @return 日志记录ID
     */
    public Long addLog(@NotNull List<ActionLog> actionLogs) {
        return addLog(actionLogs.toArray(new ActionLog[0]));
    }
    /**
     * 记录日志信息
     *
     * @param actionLogs 日志记录
     * @return 日志记录ID
     */
    public Long addLog(@NotNull ActionLog... actionLogs) {

        if (null == actionLogs) {
            return null;
        }
        try{
            //采用循环下标，降低访问用户session的次数和获取用户IP地址信息的次数
            int index = 0;
            for (ActionLog actionLog : actionLogs) {
                //操作人
                if (index++ == 0){
                    if (null == actionLog.getOperatorId()) {
                        actionLog.setOperatorName(LoginUserUtil.getLoginNickname());
                        actionLog.setOperatorId(LoginUserUtil.getLoginUserId());
                    } else if (StringUtil.isNull(actionLog.getOperatorName())) {
                        actionLog.setOperatorName(LoginUserUtil.getLoginNickname());
                    }
                    //租户ID
                    if (null == actionLog.getTenantId()) {
                        actionLog.setTenantId(LoginUserUtil.getTenantId());
                    }
                    //客户端主机IP信息
                    if (StringUtil.isNull(actionLog.getRemoteHost())) {
                        actionLog.setRemoteHost(HttpUtil.getClientIpAddress(LoginUserUtil.getRequest()));
                    }
                }else{
                    if (null == actionLog.getOperatorId()) {
                        actionLog.setOperatorName(actionLogs[0].getOperatorName());
                        actionLog.setOperatorId(actionLogs[0].getOperatorId());
                    } else if (StringUtil.isNull(actionLog.getOperatorName())) {
                        actionLog.setOperatorName(actionLogs[0].getOperatorName());
                    }
                    //租户ID
                    if (null == actionLog.getTenantId()) {
                        actionLog.setTenantId(actionLogs[0].getTenantId());
                    }
                    //客户端主机IP信息
                    if (StringUtil.isNull(actionLog.getRemoteHost())) {
                        actionLog.setRemoteHost(actionLogs[0].getRemoteHost());
                    }
                    //登入时的操作记录ID
                    actionLog.setSignId(actionLogs[0].getSignId());
                }
            }

            persist(actionLogs);
        } catch (Exception e) {
            log.error("persist action log error",e);
        }
        return null;
    }

    public Long addLog(@NotNull ActionTypeEnum type, @NotNull Object newObj, Object oldObj){
        return addLog(type,null,newObj,oldObj,null);
    }

    public Long addLog(@NotNull ActionTypeEnum type,@NotNull Object newObj, Object oldObj,String failureReason){
        return addLog(type,null,newObj,oldObj,failureReason);
    }

    /**
     * 记录日志
     * @param type 操作类型
     * @param actionName 操作描述
     * @param newObj 新数据对象 not null
     * @param oldObj 原数据对象
     * @param failureReason 失败原因 null表示成功
     * @return logId
     */
    public Long addLog(@NotNull ActionTypeEnum type,String actionName,@NotNull Object newObj, Object oldObj,String failureReason){

        // 得到类对象
        Class<?> classNew = newObj.getClass();
        try {
            EntityName modelName = classNew.getAnnotation(EntityName.class);
            if (null == modelName){
                log.error("请给参数对象"+classNew+"添加类注解:"+ EntityName.class.getName());
                return -1L;
            }

            EntityField propertyName;
            List<Object> placeholderValue = new ArrayList<>();

            final Method[] methods = null == oldObj ? classNew.getMethods() : oldObj.getClass().getMethods();
            for (Method method : methods) {
                //使用ActionLogName注解的方法来生成actionName
                if (null != method.getAnnotation(ActionLogName.class)){
                    if (method.getParameterCount()>0){
                        final Object[] objects = new Object[method.getParameterCount()];
                        Arrays.fill(objects, null);
                        placeholderValue.add(method.invoke(newObj,objects));
                    }else {
                        placeholderValue.add(method.invoke(newObj));
                    }
                }
            }
            //使用PropertyName注解的属性来生成actionName，isMain=true
            if (placeholderValue.size()==0){
                Field[] fields = null == oldObj ? classNew.getDeclaredFields() : oldObj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    // 设置属性是可以访问的(私有的也可以)
                    field.setAccessible(true);
                    propertyName = field.getAnnotation(EntityField.class);
                    if(propertyName != null && propertyName.isMain()){
                        placeholderValue.add(field.get(null == oldObj?newObj:oldObj));
                    }
                }
            }
            if (null == actionName ||  com.ssfw.common.log.annotation.ActionLog.DEFAULT.equals(actionName)){
                //未提供操作描述，使用默认描述
                actionName = type.text() + "了" + modelName.value();
                if (placeholderValue.size()>0){
                    actionName += ":"+placeholderValue.get(0);
                }
            }else {
                for (int i = 0; i < placeholderValue.size(); i++) {
                    if (actionName.contains("{"+i+"}")){
                        final Object value = placeholderValue.get(i);
                        actionName = actionName.replace("{"+i+"}", null==value?"":value.toString());
                    }
                }
            }

            List<Object> objects = ReflectUtil.getValue(newObj, TableId.class);
            if (objects.isEmpty() || objects.get(0)==null || StringUtils.isEmpty(objects.get(0).toString())){

                if (StringUtils.isNotEmpty(failureReason)){
                    //记录失败日志
                    ActionLog actionLog = ActionLog.of(newObj.getClass(), -1, type, actionName);
                    actionLog.setFailureReason(failureReason);
                    return addLog(actionLog);
                }

                log.error("参数对象"+classNew+"无主键值，未记录操作日志");
                return -1L;
            }
            final ActionLog actionLog = ActionLog.of(newObj.getClass(), objects.get(0), type, actionName);
            //失败原因
            if (StringUtils.isNotEmpty(failureReason)){
                actionLog.setFailureReason(failureReason);
            }
            String name = null;
            final ActionLogSelector actionLogSelector = classNew.getDeclaredAnnotation(ActionLogSelector.class);
            boolean logAllField = false;
            boolean onlyPropertyNameField = true;
            if (null != actionLogSelector){
                logAllField = actionLogSelector.all();
                onlyPropertyNameField = actionLogSelector.onlyPropertyNameField();
            }

            if (null == oldObj){
                //删除记录时，记录删除前的对象信息
                if (type.type() == ActionTypeEnum.TYPE_DELETE){
                    Field[] fieldsNew = classNew.getDeclaredFields();
                    for (Field fieldNew : fieldsNew) {
                        // 设置属性是可以访问的(私有的也可以)
                        fieldNew.setAccessible(true);
                        if (null != fieldNew.getAnnotation(ActionLogIgnore.class)){
                            //忽略字段
                            continue;
                        }
                        name = fieldNew.getName();
                        //得到注解
                        propertyName = fieldNew.getAnnotation(EntityField.class);
                        if (!logAllField && onlyPropertyNameField){
                            if (null == propertyName){
                                continue;
                            }
                        }

                        if ( null != propertyName){
                            name = propertyName.value();
                        }
                        Object newVal = fieldNew.get(newObj);
                        actionLog.add(ActionDetail.of(name, newVal,null));
                    }
                }
                return addLog(actionLog);
            }

            Class<?> classOld = oldObj.getClass();
            if(!classNew.getName().equals(classOld.getName())){
                throw new RuntimeException("记日志失败，请传入两个相同的实体类对象");
            }
            // 得到属性集合
            Field[] fieldsNew = classNew.getDeclaredFields();
            Field[] fieldsOld = classOld.getDeclaredFields();

            for (Field fieldNew : fieldsNew) {
                // 设置属性是可以访问的(私有的也可以)
                fieldNew.setAccessible(true);
                name = fieldNew.getName();
                if (null != fieldNew.getAnnotation(ActionLogIgnore.class)){
                    //忽略字段
                    continue;
                }
                //得到注解
                propertyName = fieldNew.getAnnotation(EntityField.class);
                if (!logAllField && onlyPropertyNameField){
                    if (null == propertyName){
                        //忽略字段
                        continue;
                    }
                }
                for (Field fieldOld : fieldsOld) {
                    // 设置属性是可以访问的(私有的也可以)
                    fieldOld.setAccessible(true);
                    // 比较属性名是否一样
                    final String fieldNewName = fieldNew.getName();
                    if(fieldNewName.equals(fieldOld.getName())){
                        final Object oldVal = fieldOld.get(oldObj);
                        final Object newVal = fieldNew.get(newObj);
                        if (isNotEquals(newVal,oldVal)){
                            if ( null != propertyName){
                                name = propertyName.value();
                            }
                            name = StringUtils.isNotEmpty(name) ? name : fieldNewName;
                            actionLog.add(ActionDetail.of(name, oldVal,newVal));
                        }
                        break;
                    }
                }
            }
            return addLog(actionLog);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(classNew.getName()+"的日志无法被记录！");
        }
        return null;
    }

    public boolean isNotEquals(Object s1, Object s2){

        if (null==s1 && null==s2) {
            return false;
        }
        if (null==s1) {
            return !"".equals(s2.toString());
        }
        if (null==s2) {
            return !"".equals(s1.toString());
        }
        if (s1 instanceof Date & s2 instanceof Date) {
            return !formatDate((Date) s1).equals(formatDate((Date) s2));
        }

        if (s1 instanceof BigDecimal & s2 instanceof BigDecimal) {
            BigDecimal decimal1 = (BigDecimal) s1;
            BigDecimal decimal2 = (BigDecimal) s2;

            return 0 != decimal1.setScale(3, RoundingMode.HALF_UP).compareTo(decimal2.setScale(3, RoundingMode.HALF_UP));
        }

        return !s1.equals(s2);
    }

    private String formatDate(Date date){

        try {
            if (null!=date) {
                return LocalDateUtil.formatDateTime(date);
            }
        } catch (Exception ignore) {
        }
        return "0000";
    }
}
