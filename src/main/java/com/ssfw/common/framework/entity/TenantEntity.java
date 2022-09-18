package com.ssfw.common.framework.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ssfw.common.framework.annotation.EntityField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author beets
 */
@Setter
@Getter
@Accessors(chain = true)
public class TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户id
     */
    @EntityField("租户id")
    private Integer tenantId;

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
