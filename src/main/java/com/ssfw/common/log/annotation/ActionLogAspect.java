package com.ssfw.common.log.annotation;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssfw.common.framework.dto.ResultDto;
import com.ssfw.common.log.manager.ActionLogManager;
import com.ssfw.common.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 记操作记录日志使用，AOP实现类，基于@ActionLog来实现日志记录功能
 * @author a
 */
@Aspect
@Order(Integer.MAX_VALUE-50)
@Component
@Slf4j
public class ActionLogAspect {

    /**
     * 定义切点 @Pointcut
     */
    @Pointcut("@annotation(com.ssfw.common.log.annotation.ActionLog)")
    public void actionLogAspect(){
    }

    private static final ThreadLocal<Data> LOCAL_DATA = new ThreadLocal<>();

    private Object getArg(JoinPoint joinPoint,MethodSignature signature){

        final Object[] args = joinPoint.getArgs();
        if (null == args || args.length == 0){
            log.error("方法无参，日志记录失败");
            return null;
        }
        final Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            int paramIndex= ArrayUtils.indexOf(parameterAnnotations, parameterAnnotation);
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof ActionObject){
                    return args[paramIndex];
                }
            }
        }
        return args[0];
    }

    @Before("actionLogAspect()")
    public void doBefore(JoinPoint joinPoint){


        // 从切面织入点处通过反射机制获取织入点处的方法
         MethodSignature signature = (MethodSignature) joinPoint.getSignature();

         //对应多个参数时，指定@ActionObject注解的参数被当为记日志用的参数
        Object arg = getArg(joinPoint,signature);
        if (null == arg){
            return;
        }

        final ActionLog annotation = signature.getMethod().getAnnotation(ActionLog.class);
        switch (annotation.type().type()){
            case 2:
                beforeUpdate(joinPoint,arg);
                break;
            case 3:
                beforeDelete(joinPoint,arg);
                break;
            default:

        }
    }

    @AfterReturning(pointcut = "actionLogAspect()",returning="rvt")
    public void doAfterReturning(JoinPoint joinPoint,Object rvt){

        ResultDto dto = null;
        if (null != rvt){
            if (rvt instanceof ResultDto){
                dto = (ResultDto) rvt;
            }
        }

        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //对应多个参数时，指定@ActionObject注解的参数被当为记日志用的参数
        Object arg = getArg(joinPoint,signature);
        if (null == arg){
            return;
        }
        final ActionLog annotation = signature.getMethod().getAnnotation(ActionLog.class);
        switch (annotation.type().type()){
            case 1:
                add(joinPoint,arg,dto);
                break;
            case 2:
                afterUpdate(joinPoint,arg,dto);
                break;
            case 3:
                afterDelete(joinPoint,arg,dto);
                break;
            case 4:
                query(joinPoint,arg,dto);
                break;
            default:

        }
    }

    private void add(JoinPoint joinPoint,final Object arg,final ResultDto<?> dto){

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final ActionLog annotation = signature.getMethod().getAnnotation(ActionLog.class);
        final String errMsg = null == dto?null:dto.getErrMsg();
        ActionLogManager.getInstance().addLog(annotation.type(),annotation.name(),arg,null,errMsg);
    }

    private void beforeUpdate(JoinPoint joinPoint,final Object arg){

        Object objectId =  ReflectUtil.getValue(arg, TableId.class).get(0);

        final IService target = (ServiceImpl)joinPoint.getTarget();
        final Object value = target.getById((Serializable) objectId);
        if (null != value){
            LOCAL_DATA.set(new Data(objectId, deepCopy(value)));
        }
    }

    private void afterUpdate(JoinPoint joinPoint,final Object arg,final ResultDto<?> dto){

        Data data = LOCAL_DATA.get();
        LOCAL_DATA.remove();
        if (null == data){
            return;
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        final ActionLog annotation = method.getAnnotation(ActionLog.class);
        final String errMsg = null == dto?null:dto.getErrMsg();
        ActionLogManager.getInstance().addLog(annotation.type(),annotation.name(),arg, data.getValue(), errMsg);

    }

    private void beforeDelete(JoinPoint joinPoint,final Object arg){

        Object objectId =  ReflectUtil.getValue(arg, TableId.class).get(0);
        final IService target = (ServiceImpl)joinPoint.getTarget();
        LOCAL_DATA.set(new Data(objectId, target.getById((Serializable) objectId)));
    }

    private void afterDelete(JoinPoint joinPoint,final Object arg,final ResultDto<?> dto){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final ActionLog annotation = signature.getMethod().getAnnotation(ActionLog.class);
        final String errMsg = null == dto?null:dto.getErrMsg();
        Data data = LOCAL_DATA.get();
        LOCAL_DATA.remove();
        ActionLogManager.getInstance().addLog(annotation.type(),annotation.name(),arg, data.getValue(),errMsg);
    }

    private void query(JoinPoint joinPoint,final Object arg,final ResultDto<?> dto){

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final ActionLog annotation = signature.getMethod().getAnnotation(ActionLog.class);
        final String errMsg = null == dto?null:dto.getErrMsg();
        ActionLogManager.getInstance().addLog(annotation.type(),annotation.name(),arg,null,errMsg);
    }



    static class Data{
        private Object objectId;
        private Object value;

        Data(Object objectId, Object value) {
            this.objectId = objectId;
            this.value = value;
        }

        public Object getObjectId() {
            return objectId;
        }

        public void setObjectId(Object objectId) {
            this.objectId = objectId;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }


    /**
     * 深度拷贝一份当前对象
     * 对象需要实现Serializable接口,并且需要给serialVersionUID赋好值
     * @return copy
     * @throws RuntimeException
     */
    @SuppressWarnings("unchecked")
    private  <T> T deepCopy(T obj)  {

        try {
            //将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out= new ObjectOutputStream(byteOut);
            //写对象，序列化
            out.writeObject(obj);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            //读对象，反序列化
            return (T) new ObjectInputStream(byteIn).readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
