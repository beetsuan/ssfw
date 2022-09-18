package com.ssfw.auth.bizcheck;


import com.ssfw.common.framework.check.BaseBizCheck;
import com.ssfw.auth.entity.AuthUserRoleEntity;
import com.ssfw.auth.service.AuthUserRoleService;

/**
 * 用户关联角色 业务验证
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:15:38
*/
public class AuthUserRoleBizCheck extends BaseBizCheck<AuthUserRoleEntity> {

    /** 用户关联角色Service */
    private final AuthUserRoleService  service;


    public AuthUserRoleBizCheck(AuthUserRoleService service) {
        super();
        this.service =service;
    }

    @Override
    public AuthUserRoleBizCheck addable(AuthUserRoleEntity entity){
        return this;
    }
    @Override
    public AuthUserRoleBizCheck deletable(AuthUserRoleEntity entity){

        if (needBizCheck()) {
            required(entity.getUserId(),"用户关联角色ID");
        }
        return this;
    }

    @Override
    public AuthUserRoleBizCheck updatable(AuthUserRoleEntity entity){

        if (needBizCheck()) {
            required(entity.getUserId(),"用户关联角色ID");
        }
        return this;
    }
}
