package com.ssfw.common.env.service;

import com.ssfw.common.framework.service.BaseService;
import com.ssfw.common.env.entity.EnvPropertiesEntity;

/**
 * 环境配置Service接口
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 15:05:09
 */
public interface EnvPropertiesService extends BaseService<EnvPropertiesEntity> {


    String CACHE_NAME = "com-env-config";

    /**
     * 获取系统环境配置
     * @param key 配置key
     * @return EnvPropertiesEntity
     */
    EnvPropertiesEntity getFromCache(String key);
}

