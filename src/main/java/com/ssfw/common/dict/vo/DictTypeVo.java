package com.ssfw.common.dict.vo;

import com.ssfw.common.dict.entity.DictTypeEntity;
import com.ssfw.common.framework.vo.ValueObjectOfEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * 数据字典ValueObject
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 15:50:42
 */
@Setter
@Getter
public class DictTypeVo implements ValueObjectOfEntity<DictTypeVo,DictTypeEntity> {

    private static final long serialVersionUID = 6428022290528938983L;

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
    public DictTypeVo of(DictTypeEntity entity){
        DictTypeVo vo = new DictTypeVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public List<DictTypeVo> of(Collection<DictTypeEntity> entities){

        final List<DictTypeVo> list = new ArrayList<>(entities.size());
        entities.forEach(user -> list.add(of(user)));
        return list;
    }

}
