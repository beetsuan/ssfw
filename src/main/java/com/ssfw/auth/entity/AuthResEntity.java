package com.ssfw.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ssfw.common.framework.annotation.EntityName;
import com.ssfw.common.framework.entity.TenantFacade;
import com.ssfw.common.log.annotation.ActionLogSelector;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 系统资源
 * 
 * @author hbq
 * @email hbq@a.com
 * @date 2022-09-16 09:53:38
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("auth_res")
@EntityName("系统资源")
@ActionLogSelector(all = true)
public class AuthResEntity implements TenantFacade<AuthResEntity>, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 资源id
	 */
	@TableId(value = "res_id", type = IdType.ASSIGN_ID)
	private Long resId;
	/**
	 * 资源名称
	 */
	private String resName;
	/**
	 * 资源编号
	 */
	private String resCode;
	/**
	 * 资源访问路径
	 */
	private String resUrl;
	/**
	 * 菜单名称
	 */
	private String menuName;
	/**
	 * 菜单图标
	 */
	private String menuIcon;
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

	public AuthResEntity(Long resId) {
		this.resId = resId;
	}


}
