package com.ssfw.common.framework.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ssfw.common.framework.annotation.EntityField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 操作时间、人字段实体
 *
 * @author beets
 */
@Setter
@Getter
@Accessors(chain = true)
public class GmtFieldEntity {

    /**
     * 创建者ID
     */
    @EntityField("创建者ID")
    @TableField(value = "creator_id",fill = FieldFill.INSERT)
    private Long creatorId;

    /**
     * 创建者
     */
    @EntityField("创建者")
    @TableField(value = "creator_name",fill = FieldFill.INSERT)
    private String creatorName;

    /**
     * 创建时间
     */
    @EntityField("创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新者ID
     */
    @EntityField("更新者ID")
    @TableField(value = "updater_id",fill = FieldFill.INSERT_UPDATE)
    private Long updaterId;

    /**
     * 更新者
     */
    @EntityField("更新者")
    @TableField(value = "updater_name",fill = FieldFill.INSERT_UPDATE)
    private String updaterName;

    /**
     * 修改时间
     */
    @EntityField("更新时间")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
