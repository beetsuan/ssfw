package com.ssfw.auth.service.impl;


import com.ssfw.common.framework.check.ValidStatus;
import com.ssfw.common.framework.service.impl.CommonServiceImpl;
import com.ssfw.common.log.annotation.ActionLog;
import com.ssfw.common.log.constant.ActionTypeEnum;
import org.springframework.stereotype.Service;
import com.ssfw.auth.mapper.AuthRoleMapper;
import com.ssfw.auth.entity.AuthRoleEntity;
import com.ssfw.auth.service.AuthRoleService;
import com.ssfw.auth.bizcheck.AuthRoleBizCheck;
/**
 * 用户角色Service实现类
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-16 15:09:19
 */
@Service
public class AuthRoleServiceImpl extends CommonServiceImpl<AuthRoleMapper, AuthRoleEntity> implements AuthRoleService {

    @Override
    public ValidStatus<AuthRoleEntity> getBizCheck() {
        return new AuthRoleBizCheck(this);
    }
}