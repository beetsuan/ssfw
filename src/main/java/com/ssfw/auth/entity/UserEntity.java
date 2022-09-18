package com.ssfw.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.ssfw.common.framework.annotation.EntityName;
import com.ssfw.common.framework.annotation.EntityField;
import com.ssfw.common.framework.entity.TenantEntity;
import com.ssfw.common.log.annotation.ActionLogSelector;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户
 *
 * @author hbq
 * @email hbq@a.com
 * @date 2022-09-18 17:18:02
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("auth_user")
@EntityName("用户")
@ActionLogSelector(all = true)
public class UserEntity extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户状态
     */
    public static final String STATUS_VALID = "valid";
    public static final String STATUS_SUSPEND = "suspended";

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    @EntityField("用户id")
    private Long userId;

    /**
     * 登录名称
     */
    @EntityField("登录名称")
    private String username;

    /**
     * 密码
     */
    @EntityField("密码")
    private String password;

    /**
     * 用户昵称
     */
    @EntityField("用户昵称")
    private String nickname;

    /**
     * 账号状态 valid有效 suspend挂起
     */
    @EntityField("账号状态 valid有效 suspend挂起")
    private String useStatus;

    /**
     * 分组id
     */
    @EntityField("分组id")
    private Long groupId;

    /**
     * 是否删除 1.删除
     */
    @EntityField("是否删除 1.删除")
    private Integer deleted;

    public UserEntity(Long userId) {
        this.userId = userId;
    }
}
