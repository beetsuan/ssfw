package com.ssfw.common.dict.service.impl;


import com.ssfw.common.cache.BaseCache;
import com.ssfw.common.framework.check.ValidStatus;
import com.ssfw.common.framework.service.impl.CommonServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import com.ssfw.common.dict.mapper.DictTypeMapper;
import com.ssfw.common.dict.entity.DictTypeEntity;
import com.ssfw.common.dict.service.DictTypeService;
import com.ssfw.common.dict.bizcheck.DictTypeBizCheck;
/**
 * 数据字典Service实现类
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 15:50:42
 */
@Service
@Slf4j
public class DictTypeServiceImpl extends CommonServiceImpl<DictTypeMapper, DictTypeEntity> implements DictTypeService {

    @Override
    public ValidStatus<DictTypeEntity> getBizCheck() {
        return new DictTypeBizCheck(this);
    }

    @CacheEvict(cacheManager = BaseCache.NAME, value = CACHE_NAME, key="#dictTypeId",beforeInvocation=true)
    @Override
    public void cacheEvict(String dictTypeId) {
        log.info("cache {} is evicted", dictTypeId);
    }
}