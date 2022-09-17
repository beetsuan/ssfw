package com.ssfw.common.dict.vo;

import com.ssfw.common.dict.entity.DictEntryEntity;
import com.ssfw.common.framework.vo.ValueObjectOfEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * 数据字典项ValueObject
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 15:48:49
 */
@Setter
@Getter
public class DictEntryVo implements ValueObjectOfEntity<DictEntryVo,DictEntryEntity> {

    private static final long serialVersionUID = 8525085880352171074L;

    /**
     * 字典ID
     */
    private String dictTypeId;
    /**
     * 数据项ID
     */
    private String dictId;
    /**
     * 数据项名称
     */
    private String dictName;
    /**
     * 状态 1.有效，2.无效
     */
    private Integer dictStatus;
    /**
     * 排序
     */
    private Integer sortNo;
    /**
     * 级别
     */
    private Integer dictRank;
    /**
     * 父级数据项ID
     */
    private String parentId;
    /**
     * 序列
     */
    private String seqNo;
    /**
     * 数据项英文名称
     */
    private String dictEnName;
    /**
     * 备注
     */
    private String remark;
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

    @Override
    public DictEntryVo of(DictEntryEntity entity){
        DictEntryVo vo = new DictEntryVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public List<DictEntryVo> of(Collection<DictEntryEntity> entities){

        final List<DictEntryVo> list = new ArrayList<>(entities.size());
        entities.forEach(user -> list.add(of(user)));
        return list;
    }

}
