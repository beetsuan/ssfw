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
 * 数据字典项
 * 
 * @author hbq
 * @email hbq@a.com
 * @date 2022-09-17 15:48:49
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("com_dict_entry")
@EntityName("数据字典项")
@ActionLogSelector(all = true)
public class DictEntryEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典ID
	 */
	@TableId(value = "dict_type_id", type = IdType.INPUT)
	@EntityField(value = "字典ID", isMain = true)
	private String dictTypeId;
	/**
	 * 数据项ID
	 */
	@EntityField(value = "数据项ID",isMain = true)
	private String dictId;
	/**
	 * 数据项名称
	 */
	@EntityField(value = "数据项名称",isMain = true)
	private String dictName;
	/**
	 * 状态 1.有效，2.无效
	 */
	@EntityField("状态 1.有效，2.无效")
	private Integer dictStatus;
	/**
	 * 排序
	 */
	@EntityField("排序")
	private Integer sortNo;
	/**
	 * 级别
	 */
	@EntityField("级别")
	private Integer dictRank;
	/**
	 * 父级数据项ID
	 */
	@EntityField("父级数据项ID")
	private String parentId;
	/**
	 * 序列
	 */
	@EntityField("序列")
	private String seqNo;
	/**
	 * 数据项英文名称
	 */
	@EntityField("数据项英文名称")
	private String dictEnName;
	/**
	 * 备注
	 */
	@EntityField("备注")
	private String remark;
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

	public DictEntryEntity(String dictTypeId, String dictId) {
		this.dictId = dictId;
		this.dictTypeId = dictTypeId;
	}
}
