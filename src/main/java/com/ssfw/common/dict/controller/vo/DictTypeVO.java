package com.ssfw.common.dict.controller.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 数据字典 ValueObject
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 16:29:10
 */
@Data
@Accessors(chain = true)
public class DictTypeVO implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 字典ID
     */
    private String dictTypeId;
    /**
     * 字典名称
     */
    private String dictTypeName;
    /**
     * 级别
     */
    private Integer dictRank;
    /**
     * 字典父类ID
     */
    private String parentId;
    /**
     * 序列
     */
    private String seqNo;
    /**
     * 字典状态 1.有效，2.无效
     */
    private Integer dictStatus;
    /**
     * 备注
     */
    private String remark;
    /**
     * 排序
     */
    private Integer sortNo;
    /**
     * 属性一
     */
    private String filter1;
    /**
     * 属性二
     */
    private String filter2;
    /**
     * 属性三
     */
    private String filter3;
    /**
     * 属性四
     */
    private String filter4;
    /**
     * 属性五
     */
    private String filter5;
    /**
     * 属性六
     */
    private String filter6;
    /**
     * 创建者ID
     */
    private Long creatorId;
    /**
     * 创建者
     */
    private String creatorName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新者ID
     */
    private Long updaterId;
    /**
     * 更新者
     */
    private String updaterName;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
