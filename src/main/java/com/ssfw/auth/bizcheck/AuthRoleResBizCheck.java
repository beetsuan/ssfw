package com.ssfw.auth.bizcheck;


import com.ssfw.common.framework.check.BaseBizCheck;
import com.ssfw.auth.entity.AuthRoleResEntity;
import com.ssfw.auth.service.AuthRoleResService;

/**
 * 角色权限 业务验证
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:14:53
*/
public class AuthRoleResBizCheck extends BaseBizCheck<AuthRoleResEntity> {

    /** 角色权限Service */
    private final AuthRoleResService  service;


    public AuthRoleResBizCheck(AuthRoleResService service) {
        super();
        this.service =service;
    }

    @Override
    public AuthRoleResBizCheck addable(AuthRoleResEntity entity){
        return this;
    }
    @Override
    public AuthRoleResBizCheck deletable(AuthRoleResEntity entity){

        if (needBizCheck()) {
            required(entity.getRoleId(),"角色权限ID");
        }
        return this;
    }

    @Override
    public AuthRoleResBizCheck updatable(AuthRoleResEntity entity){

        if (needBizCheck()) {
            required(entity.getRoleId(),"角色权限ID");
        }
        return this;
    }
}
