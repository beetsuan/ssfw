package com.ssfw.auth.bizcheck;


import com.ssfw.common.framework.check.BaseBizCheck;
import com.ssfw.auth.entity.AuthRoleEntity;
import com.ssfw.auth.service.AuthRoleService;

/**
 * 用户角色 业务验证
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-16 15:09:19
*/
public class AuthRoleBizCheck extends BaseBizCheck<AuthRoleEntity> {

    /** 用户角色Service */
    private final AuthRoleService  service;


    public AuthRoleBizCheck(AuthRoleService service) {
        super();
        this.service =service;
    }

    @Override
    public AuthRoleBizCheck addable(AuthRoleEntity entity){
        return this;
    }
    @Override
    public AuthRoleBizCheck deletable(AuthRoleEntity entity){

        if (needBizCheck()) {
            required(entity.getRoleId(),"用户角色ID");
        }
        return this;
    }

    @Override
    public AuthRoleBizCheck updatable(AuthRoleEntity entity){

        if (needBizCheck()) {
            required(entity.getRoleId(),"用户角色ID");
        }
        return this;
    }
}
