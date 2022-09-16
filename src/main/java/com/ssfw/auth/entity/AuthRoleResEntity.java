package com.ssfw.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ssfw.common.framework.annotation.EntityName;
import com.ssfw.common.log.annotation.ActionLogSelector;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色权限
 * 
 * @author hbq
 * @email hbq@a.com
 * @date 2022-09-16 10:27:26
 */
@Data
@NoArgsConstructor
@TableName("auth_role_res")
@EntityName("角色权限")
@ActionLogSelector(all = true)
public class AuthRoleResEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	@TableId(value = "role_id", type = IdType.ASSIGN_ID)
	private Long roleId;
	/**
	 * 资源id
	 */
	private Long resId;
	/**
	 * 授权日期
	 */
	private LocalDateTime authDate;

	public AuthRoleResEntity(Long roleId) {
		this.roleId = roleId;
	}
}
