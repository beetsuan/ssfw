package com.ssfw.common.env.service.impl;


import com.ssfw.common.cache.BaseCache;
import com.ssfw.common.framework.check.ValidStatus;
import com.ssfw.common.framework.service.impl.BaseServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.ssfw.common.env.mapper.EnvPropertiesMapper;
import com.ssfw.common.env.entity.EnvPropertiesEntity;
import com.ssfw.common.env.service.EnvPropertiesService;
import com.ssfw.common.env.bizcheck.EnvPropertiesBizCheck;
/**
 * 环境配置Service实现类
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 15:05:09
 */
@Service
public class EnvPropertiesServiceImpl extends BaseServiceImpl<EnvPropertiesMapper, EnvPropertiesEntity> implements EnvPropertiesService {

    @Override
    public ValidStatus<EnvPropertiesEntity> getBizCheck() {
        return new EnvPropertiesBizCheck(this);
    }

    @Override
    @Cacheable(cacheManager = BaseCache.NAME, value = CACHE_NAME, key = "#key")
    public EnvPropertiesEntity getFromCache(String key) {
        return getById(key);
    }
}