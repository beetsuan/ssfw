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
import lombok.experimental.Accessors;

/**
 * 用户角色
 * 
 * @author hbq
 * @email hbq@a.com
 * @date 2022-09-16 15:09:19
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("auth_role")
@EntityName("用户角色")
@ActionLogSelector(all = true)
public class AuthRoleEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	@TableId(value = "role_id", type = IdType.ASSIGN_ID)
	private Long roleId;
	/**
	 * 角色code
	 */
	private String roleCode;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 描述
	 */
	private String roleDesc;
	/**
	 * 租户id
	 */
	private Integer tenantId;
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

	public AuthRoleEntity(Long roleId) {
		this.roleId = roleId;
	}
}
