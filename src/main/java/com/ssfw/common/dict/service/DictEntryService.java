package com.ssfw.common.dict.service;

import com.ssfw.common.dict.entity.DictEntryEntity;
import com.ssfw.common.framework.service.CommonService;

import javax.validation.constraints.NotNull;

/**
 * 数据字典项Service接口
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-17 15:48:49
 */
public interface DictEntryService extends CommonService<DictEntryEntity> {

    DictEntryEntity get(@NotNull String dictTypeId, @NotNull String dictId);
}

