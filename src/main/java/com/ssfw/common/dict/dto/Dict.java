package com.ssfw.common.dict.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssfw.common.dict.entity.DictEntryEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 字典数据
 * @author a
 * @date 2016年9月21日 下午9:49:13
 */
@Data
@Accessors(chain = true)
public class Dict implements Serializable{

	private static final long serialVersionUID = -4336250122824773777L;

	public final static Integer STATUS_ON = 1;
	public final static Integer STATUS_OFF = 2;

	/**
	 * 字典数据ID
	 */
	private String id;
	/**
	 * 字典数据名称
	 */
	private String text;
	/**
	 * 字典数据英文名称
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String enText;
	/**
	 * 字典数据父类ID
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String pid;
	/**
	 * 字典分类名称
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String pText;
	/**
	 * 字典分类父级名称
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String ppText;
	/**
	 * 排序
	 */
	private Integer no;
	/**
	 * 字典
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String type;
	/**
	 * 字典状态 1.有效，2.无效
	 */
	private Integer status;
	/**
	 * 属性一
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String filter1;
	/**
	 * 属性二
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String filter2;
	/**
	 * 属性三
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String filter3;
	/**
	 * 属性四
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String filter4;
	/**
	 * 属性五
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String filter5;
	/**
	 * 属性六
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String filter6;

	public static Dict of(DictEntryEntity entity) {
		return null;
	}

	public DictEntryEntity toDictEntry() {

		DictEntryEntity entry = new DictEntryEntity();
		BeanUtils.copyProperties(this,entry);

		entry.setDictTypeId(getType());
		entry.setDictId(getId());
		entry.setDictName(getText());
		entry.setDictEnName(getEnText());
		entry.setDictStatus(getStatus());
		entry.setSortNo(getNo());
		entry.setParentId(getPid());

		return entry;
	}
}
