package com.ssfw.common.dict.entity;

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
 * 数据字典
 * 
 * @author hbq
 * @email hbq@a.com
 * @date 2022-09-17 15:50:42
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("com_dict_type")
@EntityName("数据字典")
@ActionLogSelector(all = true)
public class DictTypeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典ID
	 */
	@TableId(value = "dict_type_id", type = IdType.AUTO)
	@EntityField("字典ID")
	private String dictTypeId;
	/**
	 * 字典名称
	 */
	@EntityField("字典名称")
	private String dictTypeName;
	/**
	 * 级别
	 */
	@EntityField("级别")
	private Integer dictRank;
	/**
	 * 字典父类ID
	 */
	@EntityField("字典父类ID")
	private String parentId;
	/**
	 * 序列
	 */
	@EntityField("序列")
	private String seqNo;
	/**
	 * 字典状态 1.有效，2.无效
	 */
	@EntityField("字典状态 1.有效，2.无效")
	private Integer dictStatus;
	/**
	 * 备注
	 */
	@EntityField("备注")
	private String remark;
	/**
	 * 排序
	 */
	@EntityField("排序")
	private Integer sortNo;
	/**
	 * 属性一
	 */
	@EntityField("属性一")
	private String filter1;
	/**
	 * 属性二
	 */
	@EntityField("属性二")
	private String filter2;
	/**
	 * 属性三
	 */
	@EntityField("属性三")
	private String filter3;
	/**
	 * 属性四
	 */
	@EntityField("属性四")
	private String filter4;
	/**
	 * 属性五
	 */
	@EntityField("属性五")
	private String filter5;
	/**
	 * 属性六
	 */
	@EntityField("属性六")
	private String filter6;
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

	public DictTypeEntity(String dictTypeId) {
		this.dictTypeId = dictTypeId;
	}
}
