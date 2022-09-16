package com.ssfw.auth.service.impl;


import com.ssfw.common.framework.check.ValidStatus;
import com.ssfw.common.framework.service.impl.CommonServiceImpl;
import com.ssfw.common.log.annotation.ActionLog;
import com.ssfw.common.log.constant.ActionTypeEnum;
import org.springframework.stereotype.Service;
import com.ssfw.auth.mapper.AuthUserRoleMapper;
import com.ssfw.auth.entity.AuthUserRoleEntity;
import com.ssfw.auth.service.AuthUserRoleService;
import com.ssfw.auth.bizcheck.AuthUserRoleBizCheck;
/**
 * 用户关联角色Service实现类
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-16 15:06:12
 */
@Service
public class AuthUserRoleServiceImpl extends CommonServiceImpl<AuthUserRoleMapper, AuthUserRoleEntity> implements AuthUserRoleService {

    @Override
    public ValidStatus<AuthUserRoleEntity> getBizCheck() {
        return new AuthUserRoleBizCheck(this);
    }
}