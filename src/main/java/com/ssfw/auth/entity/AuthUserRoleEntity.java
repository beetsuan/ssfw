package com.ssfw.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ssfw.common.framework.annotation.EntityName;
import com.ssfw.common.log.annotation.ActionLogSelector;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户关联角色
 * 
 * @author hbq
 * @email hbq@a.com
 * @date 2022-09-16 15:06:12
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("auth_user_role")
@EntityName("用户关联角色")
@ActionLogSelector(all = true)
public class AuthUserRoleEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId(value = "user_id", type = IdType.ASSIGN_ID)
	private Long userId;
	/**
	 * 角色id
	 */
	private Long roleId;

	public AuthUserRoleEntity(Long userId) {
		this.userId = userId;
	}
}
