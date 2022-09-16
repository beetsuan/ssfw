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
 * 用户小组
 * 
 * @author hbq
 * @email hbq@a.com
 * @date 2022-09-15 17:38:29
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("auth_group")
@EntityName("用户小组")
@ActionLogSelector(all = true)
public class AuthGroupEntity implements TenantFacade<AuthGroupEntity>, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 小组id
	 */
	@TableId(value = "group_id", type = IdType.ASSIGN_ID)
	private Long groupId;
	/**
	 * 小组名称
	 */
	private String groupName;
	/**
	 * 小组编号
	 */
	private String groupCode;
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

	public AuthGroupEntity(Long groupId) {
		this.groupId = groupId;
	}
}
