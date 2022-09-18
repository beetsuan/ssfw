package com.ssfw.common.log.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ssfw.common.log.constant.ActionTypeEnum;
import com.ssfw.common.util.ReflectUtil;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 操作日志记录
 * </p>
 *
 * @author ssfw
 * @since 2022-09-14
 */
@TableName("com_action_log")
@Setter
@Getter
public class ActionLog implements Serializable {



    private static final long serialVersionUID = 3097990504163132697L;
    /**
     * 用户id
     */
    @TableId(value = "action_id", type = IdType.AUTO)
    private Long actionId;
    private String actionType;
    /**
     * 操作名称 描述
     */
    private String actionName;
    /**
     * 租户id
     */
    private Integer tenantId;
    private String objectId;
    /**
     *  类名称
     */
    private String objectType;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    private Long operatorId;
    /**
     * 避免用户更改了显示名称
     */
    private String operatorName;
    /**
     * 失败原因 null表示没有失败
     */
    private String failureReason;
    /**
     * 客户端管理ID APP管理时可以提供
     */
    private String clientId;
    /**
     * 客户端主机IP地址
     */
    private String remoteHost;
    private String remark;
    /**
     * 登入记录ID
     */
    private Long signId;
    /**
     *
     主记录ID null表示是主记录
     */
    private Long masterId;

    /**
     * //detailList size
     */
    private Integer actionCount;

    @TableField(exist = false)
    private List<ActionDetail> detailList = new ArrayList<>();



    public ActionLog(){
        super();
    }

    public ActionLog(Class<?> clazz,Object objectId,String actionType,String actionName){
        super();
        setObjectType(clazz);
        if (null==objectId){
            objectId=0;
        }
        this.objectId  = objectId+"";
        this.actionType = actionType;
        this.actionName =actionName;
    }


    public static ActionLog of(Object entity, ActionTypeEnum actionType, String actionName){

        return ActionLog.of(entity,actionType.text(),actionName);
    }

    public static ActionLog of(Object entity,String actionType,String actionName){

        Object objectId = ReflectUtil.getValue(entity, TableId.class);

        return new ActionLog(entity.getClass(),objectId,actionType,actionName);
    }

    public static ActionLog of(Class<?> clazz,Object objectId,String actionType,String actionName){
        return new ActionLog(clazz,objectId,actionType,actionName);
    }

    public static ActionLog of(Class<?> clazz,Object objectId,ActionTypeEnum actionType,String actionName){
        return new ActionLog(clazz,objectId,actionType.text(),actionName);
    }
    public void add(ActionDetail actionDetail){
        getDetailList().add(actionDetail);
    }


    public void setObjectId(Object objectId) {
        if(null!=objectId) {
            this.objectId = objectId.toString();
        } else {
            this.objectId = null;
        }
    }

    public void setObjectType(Class<?> clazz) {
        if (null == clazz){
            this.objectType = null;
            return;
        }
        this.objectType = clazz.getName().replace("com.ssfw.","");
    }

}
