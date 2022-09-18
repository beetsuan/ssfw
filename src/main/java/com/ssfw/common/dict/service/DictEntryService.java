package com.ssfw.common.dict.service;

import com.ssfw.common.dict.entity.DictEntryEntity;
import com.ssfw.common.framework.service.BaseService;

import javax.validation.constraints.NotNull;

/**
 * 数据字典项Service接口
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 15:48:49
 */
public interface DictEntryService extends BaseService<DictEntryEntity> {

    /**
     * 获取字典项
     * @param dictTypeId 字典id
     * @param dictId 字典项id
     * @return DictEntryEntity
     */
    DictEntryEntity get(@NotNull String dictTypeId, @NotNull String dictId);
}

