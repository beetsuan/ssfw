package com.ssfw.common.dict.bizcheck;


import com.ssfw.common.centext.SpringContextHolder;
import com.ssfw.common.dict.entity.DictTypeEntity;
import com.ssfw.common.dict.mapper.DictTypeMapper;
import com.ssfw.common.framework.check.BaseBizCheck;
import com.ssfw.common.dict.entity.DictEntryEntity;
import com.ssfw.common.dict.service.DictEntryService;

/**
 * 数据字典项 业务验证
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 15:48:49
*/
public class DictEntryBizCheck extends BaseBizCheck<DictEntryEntity> {

    /** 数据字典项Service */
    private final DictEntryService  service;

    private final DictTypeMapper typeMapper;


    public DictEntryBizCheck(DictEntryService service) {
        super();
        this.service =service;
        this.typeMapper = SpringContextHolder.getBean(DictTypeMapper.class);
    }

    @Override
    public DictEntryBizCheck addable(DictEntryEntity entity){


        if (!existType(entity) || existEntity(entity)){
            throwIfError();
        }
        return this;
    }
    @Override
    public DictEntryBizCheck deletable(DictEntryEntity entity){

        if (needBizCheck()) {
            required(entity.getDictTypeId(),"数据字典ID");
            required(entity.getDictId(),"数据字典项ID");
        }
        return this;
    }

    @Override
    public DictEntryBizCheck updatable(DictEntryEntity entity){

        if (!existType(entity) || existEntity(entity)){
            throwIfError();
        }
        return this;
    }

    private boolean existType(DictEntryEntity entity){

        DictTypeEntity type = typeMapper.selectById(entity.getDictTypeId());
        if (type == null) {
            addError("字典不存在");
            return false;
        }
        return true;
    }

    private boolean existEntity(DictEntryEntity entity){

        String typeId = entity.getDictTypeId();
        String dictId = entity.getDictId();

        if (needBizCheck()) {
            required(typeId,"数据字典ID");
            required(dictId,"数据字典项ID");
        }

        DictEntryEntity entry = service.get(typeId, dictId);
        if (null != entry){
            addError("已存在编码相同的字典数据，字典:{},字典数据:{}", typeId, dictId);
            return true;
        }
        return false;
    }
}
