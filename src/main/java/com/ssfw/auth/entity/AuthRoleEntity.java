package com.ssfw.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ssfw.common.framework.annotation.EntityName;
import com.ssfw.common.framework.annotation.EntityField;
import com.ssfw.common.framework.entity.GmtFieldEntity;
import com.ssfw.common.framework.entity.TenantEntity;
import com.ssfw.common.log.annotation.ActionLogSelector;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户角色
 * 
 * @author hbq
 * @email hbq@a.com
 * @date 2022-09-18 17:14:41
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("auth_role")
@EntityName("用户角色")
@ActionLogSelector(all = true)
public class AuthRoleEntity extends TenantEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	@TableId(value = "role_id", type = IdType.AUTO)
	@EntityField("角色id")
	private Long roleId;

	/**
	 * 角色code
	 */
	@EntityField("角色code")
	private String roleCode;

	/**
	 * 角色名称
	 */
	@EntityField("角色名称")
	private String roleName;

	/**
	 * 描述
	 */
	@EntityField("描述")
	private String roleDesc;


	public AuthRoleEntity(Long roleId) {
		this.roleId = roleId;
	}
}
