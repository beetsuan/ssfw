package com.ssfw.common.env.service;

import com.ssfw.common.env.entity.EnvPropertiesEntity;
import com.ssfw.common.framework.service.CommonService;

/**
 * 环境配置Service接口
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 16:00:34
 */
public interface EnvPropertiesService extends CommonService<EnvPropertiesEntity> {


    String CACHE_NAME = "com-env-config";

    EnvPropertiesEntity getFromCache(String key);
}

