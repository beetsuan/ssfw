package com.ssfw.common.dict.service;

import com.ssfw.common.dict.entity.DictTypeEntity;
import com.ssfw.common.framework.service.BaseService;

/**
 * 数据字典Service接口
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 15:50:42
 */
public interface DictTypeService extends BaseService<DictTypeEntity> {


    String CACHE_NAME = "com-dict";

    /**
     * 将字典缓存失效
     * @param dictTypeId 字典ID
     */
    void cacheEvict(String dictTypeId);
}

