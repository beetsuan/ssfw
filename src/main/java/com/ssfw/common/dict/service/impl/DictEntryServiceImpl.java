package com.ssfw.common.dict.service.impl;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.ssfw.common.dict.bizcheck.DictEntryBizCheck;
import com.ssfw.common.framework.check.ValidStatus;
import com.ssfw.common.framework.service.impl.CommonServiceImpl;
import com.ssfw.common.log.annotation.ActionLog;
import com.ssfw.common.log.constant.ActionTypeEnum;
import org.springframework.stereotype.Service;
import com.ssfw.common.dict.mapper.DictEntryMapper;
import com.ssfw.common.dict.entity.DictEntryEntity;
import com.ssfw.common.dict.service.DictEntryService;

import java.util.List;

/**
 * 数据字典项Service实现类
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 15:48:49
 */
@Service
public class DictEntryServiceImpl extends CommonServiceImpl<DictEntryMapper, DictEntryEntity> implements DictEntryService {

    @Override
    public ValidStatus<DictEntryEntity> getBizCheck() {
        return new DictEntryBizCheck(this);
    }



    @ActionLog(type = ActionTypeEnum.DELETE)
    @Override
    public boolean removeById(DictEntryEntity entity) {

        LambdaQueryChainWrapper<DictEntryEntity> wrapper = lambdaQuery()
                .eq(DictEntryEntity::getDictId, entity.getDictId())
                .eq(DictEntryEntity::getDictTypeId, entity.getDictTypeId());
        
        return super.remove(wrapper);
    }

    @Override
    public DictEntryEntity get(String dictTypeId, String dictId) {

        List<DictEntryEntity> list = lambdaQuery()
                .eq(DictEntryEntity::getDictId, dictId)
                .eq(DictEntryEntity::getDictTypeId, dictTypeId).list();

        return list.isEmpty()?null:list.get(0);
    }
}