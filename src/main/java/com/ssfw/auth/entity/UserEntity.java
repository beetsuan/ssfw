package com.ssfw.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ssfw.common.framework.annotation.EntityName;
import com.ssfw.common.framework.entity.TenantFacade;
import com.ssfw.common.log.annotation.ActionLogSelector;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 用户 实体
 * @author a
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("auth_user")
@EntityName("用户")
@ActionLogSelector(all = true)
public class UserEntity implements TenantFacade<UserEntity>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户状态
     */
    public static final String STATUS_VALID = "valid";
    public static final String STATUS_SUSPEND = "suspended";

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    /**
     * 登录名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 账号状态 valid有效 suspended挂起
     */
    private String useStatus;

    /**
     * 分组id
     */
    private Long groupId;

    /**
     * 租户id
     */
    private Integer tenantId;

    /**
     * 逻辑删除
     * 已删除值(1)，未删除值(0)
     */
    @TableLogic
    private Integer deleted;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;

    public UserEntity(Long userId) {
        this.userId = userId;
    }
}
