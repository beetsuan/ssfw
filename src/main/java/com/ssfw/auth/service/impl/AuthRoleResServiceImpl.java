package com.ssfw.auth.service.impl;


import com.ssfw.common.framework.check.ValidStatus;
import com.ssfw.common.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.ssfw.auth.mapper.AuthRoleResMapper;
import com.ssfw.auth.entity.AuthRoleResEntity;
import com.ssfw.auth.service.AuthRoleResService;
import com.ssfw.auth.bizcheck.AuthRoleResBizCheck;
/**
 * 角色权限Service实现类
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-16 10:27:26
 */
@Service
public class AuthRoleResServiceImpl extends BaseServiceImpl<AuthRoleResMapper, AuthRoleResEntity> implements AuthRoleResService {

    @Override
    public ValidStatus<AuthRoleResEntity> getBizCheck() {
        return new AuthRoleResBizCheck(this);
    }
}