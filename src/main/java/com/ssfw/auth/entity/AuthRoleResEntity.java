package com.ssfw.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ssfw.common.framework.annotation.EntityName;
import com.ssfw.common.framework.annotation.EntityField;
import com.ssfw.common.log.annotation.ActionLogSelector;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 角色权限
 * 
 * @author hbq
 * @email hbq@a.com
 * @date 2022-09-18 17:14:53
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("auth_role_res")
@EntityName("角色权限")
@ActionLogSelector(all = true)
public class AuthRoleResEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	@TableId(value = "role_id", type = IdType.AUTO)
	@EntityField("角色id")
	private Long roleId;

	/**
	 * 资源id
	 */
	@EntityField("资源id")
	private Long resId;

	/**
	 * 授权日期
	 */
	@EntityField("授权日期")
	private LocalDateTime authDate;

	public AuthRoleResEntity(Long roleId) {
		this.roleId = roleId;
	}
}
