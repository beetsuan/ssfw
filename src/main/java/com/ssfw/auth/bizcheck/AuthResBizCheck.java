package com.ssfw.auth.bizcheck;


import com.ssfw.common.framework.check.BaseBizCheck;
import com.ssfw.auth.entity.AuthResEntity;
import com.ssfw.auth.service.AuthResService;

/**
 * 系统资源 业务验证
 *
 * @author <a href="hbq@a.com">hbq</a>
 * @date 2022-09-18 17:13:57
*/
public class AuthResBizCheck extends BaseBizCheck<AuthResEntity> {

    /** 系统资源Service */
    private final AuthResService  service;


    public AuthResBizCheck(AuthResService service) {
        super();
        this.service =service;
    }

    @Override
    public AuthResBizCheck addable(AuthResEntity entity){
        return this;
    }
    @Override
    public AuthResBizCheck deletable(AuthResEntity entity){

        if (needBizCheck()) {
            required(entity.getResId(),"系统资源ID");
        }
        return this;
    }

    @Override
    public AuthResBizCheck updatable(AuthResEntity entity){

        if (needBizCheck()) {
            required(entity.getResId(),"系统资源ID");
        }
        return this;
    }
}
