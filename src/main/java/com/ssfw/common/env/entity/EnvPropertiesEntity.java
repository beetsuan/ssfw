package com.ssfw.common.env.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ssfw.common.framework.annotation.EntityName;
import com.ssfw.common.framework.annotation.EntityField;
import com.ssfw.common.log.annotation.ActionLogName;
import com.ssfw.common.log.annotation.ActionLogSelector;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.ssfw.common.util.StringUtil;
import lombok.Data;

/**
 * 环境配置
 * 
 * @author hbq
 * @email hbq@a.com
 * @date 2022-09-17 16:00:34
 */
@Data
@TableName("com_env_properties")
@EntityName("环境配置")
@ActionLogSelector(all = true)
public class EnvPropertiesEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 配置键
	 */
	@TableId(value = "prop_key", type = IdType.INPUT)
	@EntityField("配置键")
	private String key;
	/**
	 * 配置说明
	 */
	@EntityField("配置说明")
	@TableField(value = "prop_title")
	private String title;
	/**
	 * 配置值1
	 */
	@EntityField("配置值1")
	private String value01;
	/**
	 * 配置值2
	 */
	@EntityField("配置值2")
	private String value02;
	/**
	 * 配置值3
	 */
	@EntityField("配置值3")
	private String value03;
	/**
	 * 配置值4
	 */
	@EntityField("配置值4")
	private String value04;
	/**
	 * 配置值5
	 */
	@EntityField("配置值5")
	private String value05;
	/**
	 * 创建人
	 */
	@EntityField("创建人")
	private String createUser;
	/**
	 * 创建时间
	 */
	@EntityField("创建时间")
	private LocalDateTime createDate;
	/**
	 * 修改人
	 */
	@EntityField("修改人")
	private String updateUser;
	/**
	 * 修改时间
	 */
	@EntityField("修改时间")
	private LocalDateTime updateDate;

	public EnvPropertiesEntity() {
	}

	public EnvPropertiesEntity(String key) {
		this.key = key;
	}

	public static EnvPropertiesEntity off() {

		EnvPropertiesEntity properties = new EnvPropertiesEntity();
		properties.setValue01("off");
		return properties;
	}

	public boolean isOn(){
		return "true".equalsIgnoreCase(getValue01()) || "on".equalsIgnoreCase(getValue01()) || "ok".equalsIgnoreCase(getValue01());
	}

	@ActionLogName
	public String toLogString(){
		return StringUtil.join( "键:", getKey(), "，描述:", getTitle());
	}
}
