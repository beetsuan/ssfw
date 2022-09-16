package com.ssfw.auth.service.impl;


import com.ssfw.common.framework.check.ValidStatus;
import com.ssfw.common.framework.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import com.ssfw.auth.mapper.AuthResMapper;
import com.ssfw.auth.entity.AuthResEntity;
import com.ssfw.auth.service.AuthResService;
import com.ssfw.auth.bizcheck.AuthResBizCheck;
/**
 * 系统资源Service实现类
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-15 17:01:05
 */
@Service
public class AuthResServiceImpl extends CommonServiceImpl<AuthResMapper, AuthResEntity> implements AuthResService {

    @Override
    public ValidStatus<AuthResEntity> getBizCheck() {
        return new AuthResBizCheck(this);
    }
}