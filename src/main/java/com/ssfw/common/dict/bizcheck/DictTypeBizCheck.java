package com.ssfw.common.dict.bizcheck;


import com.ssfw.common.framework.check.BaseBizCheck;
import com.ssfw.common.dict.entity.DictTypeEntity;
import com.ssfw.common.dict.service.DictTypeService;

/**
 * 数据字典 业务验证
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 15:50:42
*/
public class DictTypeBizCheck extends BaseBizCheck<DictTypeEntity> {

    /** 数据字典Service */
    private final DictTypeService  service;


    public DictTypeBizCheck(DictTypeService service) {
        super();
        this.service =service;
    }

    @Override
    public DictTypeBizCheck addable(DictTypeEntity entity){

        if (service.getById(entity.getDictTypeId()) != null){
            addError("数据字典 {} 已存在", entity.getDictTypeId());
        }
        return this;
    }
    @Override
    public DictTypeBizCheck deletable(DictTypeEntity entity){

        if (needBizCheck()) {
            required(entity.getDictTypeId(),"数据字典ID");
        }
        return this;
    }

    @Override
    public DictTypeBizCheck updatable(DictTypeEntity entity){

        if (needBizCheck()) {
            required(entity.getDictTypeId(),"数据字典ID");
        }
        return this;
    }
}
