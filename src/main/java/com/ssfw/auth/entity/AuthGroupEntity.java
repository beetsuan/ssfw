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
 * 用户小组
 * 
 * @author hbq
 * @email hbq@a.com
 * @date 2022-09-18 17:08:01
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("auth_group")
@EntityName("用户小组")
@ActionLogSelector(all = true)
public class AuthGroupEntity extends TenantEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 小组id
	 */
	@TableId(value = "group_id", type = IdType.AUTO)
	@EntityField("小组id")
	private Long groupId;

	/**
	 * 小组名称
	 */
	@EntityField("小组名称")
	private String groupName;

	/**
	 * 小组编号
	 */
	@EntityField("小组编号")
	private String groupCode;


	public AuthGroupEntity(Long groupId) {
		this.groupId = groupId;
	}
}
