package com.ssfw.common.dict.controller.cmd;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * 数据字典项 CreateCommand
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 16:30:56
 */
@Setter
@Getter
public class DictEntryCreateCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典ID
     */
    @Length(max = 128)
    private String dictTypeId;
    /**
     * 数据项ID
     */
    @Length(min = 1, max = 128)
    @NotBlank
    private String dictId;

    /**
     * 数据项名称
     */
    @Length(min = 1, max = 500)
    @NotBlank
    private String dictName;

    /**
     * 状态 1.有效，2.无效
     */
    @NotNull
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Integer dictStatus;

    /**
     * 排序
     */
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Integer sortNo;

    /**
     * 级别
     */
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Integer dictRank;

    /**
     * 父级数据项ID
     */
    @Length(max = 255)
    private String parentId;

    /**
     * 序列
     */
    @Length(max = 255)
    private String seqNo;

    /**
     * 数据项英文名称
     */
    @Length(max = 500)
    private String dictEnName;

    /**
     * 备注
     */
    @Length(max = 1000)
    private String remark;

    /**
     * 属性一
     */
    @Length(max = 1000)
    private String filter1;

    /**
     * 属性二
     */
    @Length(max = 1000)
    private String filter2;

    /**
     * 属性三
     */
    @Length(max = 1000)
    private String filter3;

    /**
     * 属性四
     */
    @Length(max = 1000)
    private String filter4;

    /**
     * 属性五
     */
    @Length(max = 1000)
    private String filter5;

    /**
     * 属性六
     */
    @Length(max = 1000)
    private String filter6;

}
