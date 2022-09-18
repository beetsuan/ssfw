package com.ssfw.auth.service.impl;


import com.ssfw.common.framework.check.ValidStatus;
import com.ssfw.common.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.ssfw.auth.mapper.AuthGroupMapper;
import com.ssfw.auth.entity.AuthGroupEntity;
import com.ssfw.auth.service.AuthGroupService;
import com.ssfw.auth.bizcheck.AuthGroupBizCheck;
/**
 * 用户小组Service实现类
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-15 17:19:00
 */
@Service
public class AuthGroupServiceImpl extends BaseServiceImpl<AuthGroupMapper, AuthGroupEntity> implements AuthGroupService {

    @Override
    public ValidStatus<AuthGroupEntity> getBizCheck() {
        return new AuthGroupBizCheck(this);
    }
}