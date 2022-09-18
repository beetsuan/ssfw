package com.ssfw.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ssfw.common.framework.annotation.EntityName;
import com.ssfw.common.framework.annotation.EntityField;
import com.ssfw.common.framework.entity.TenantEntity;
import com.ssfw.common.log.annotation.ActionLogSelector;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 系统资源
 * 
 * @author hbq
 * @email hbq@a.com
 * @date 2022-09-18 17:13:57
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("auth_res")
@EntityName("系统资源")
@ActionLogSelector(all = true)
public class AuthResEntity extends TenantEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 资源id
	 */
	@TableId(value = "res_id", type = IdType.AUTO)
	@EntityField("资源id")
	private Long resId;

	/**
	 * 资源名称
	 */
	@EntityField("资源名称")
	private String resName;

	/**
	 * 资源编号
	 */
	@EntityField("资源编号")
	private String resCode;

	/**
	 * 资源访问路径
	 */
	@EntityField("资源访问路径")
	private String resUrl;

	/**
	 * 菜单名称
	 */
	@EntityField("菜单名称")
	private String menuName;

	/**
	 * 菜单图标
	 */
	@EntityField("菜单图标")
	private String menuIcon;

	public AuthResEntity(Long resId) {
		this.resId = resId;
	}
}
