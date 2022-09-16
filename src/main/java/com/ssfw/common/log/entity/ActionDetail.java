package com.ssfw.common.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ssfw.common.util.LocalDateUtil;
import com.ssfw.common.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * <p>
 * 操作日志记录明细
 * </p>
 *
 * @author ssfw
 * @since 2022-09-14
 */
@TableName("sp_action_detail")
@Setter
@Getter
public class ActionDetail {

    private static final long serialVersionUID = -2561576446552971877L;

    @TableId(value = "detail_id", type = IdType.AUTO)
    private Long detailId;
    private Long actionId;
    private String fieldName;
    private String oldVal;
    private String newVal;

    public ActionDetail( ) {
        super();
    }

    public ActionDetail(String fieldName) {
        this( fieldName,null,null);
    }

    public ActionDetail(String fieldName, String oldVal, String newVal) {
        super();
        this.fieldName = fieldName;
        this.oldVal = oldVal;
        this.newVal = newVal;
    }

    public static ActionDetail of(String fieldName, Object oldVal, Object newVal){
        ActionDetail actionDetail = new ActionDetail(fieldName);
        if (null != newVal){
            if (newVal instanceof Date){
                //日期处理
                actionDetail.setNewVal(LocalDateUtil.formatDateTime((Date) newVal));
            }else {
                actionDetail.setNewVal(newVal.toString());
            }
        }
        if (null != oldVal){
            if (oldVal instanceof Date){
                //日期处理
                actionDetail.setOldVal(LocalDateUtil.formatDateTime((Date) oldVal));
            }else {
                actionDetail.setOldVal(oldVal.toString());
            }
        }
        return actionDetail;
    }

    public void setOldVal(String oldVal,String emptyText) {
        if (StringUtil.isNull(oldVal)) {
            this.oldVal = emptyText;
        } else {
            this.oldVal = oldVal;
        }
    }

    public void setNewVal(String newVal,String emptyText) {
        if (StringUtil.isNull(newVal)) {
            this.newVal = emptyText;
        } else {
            this.newVal = newVal;
        }
    }
}
