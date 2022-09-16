package com.ssfw.auth.bizcheck;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssfw.auth.entity.UserEntity;
import com.ssfw.auth.service.AuthUserService;
import com.ssfw.common.framework.check.BaseBizCheck;

/**
 * @author a
 * @date 2022/9/15
 * @since 2.7.3
 */
public class AuthUserBizCheck extends BaseBizCheck<UserEntity> {

    private final AuthUserService authUserService;

    public AuthUserBizCheck(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @Override
    public AuthUserBizCheck addable(UserEntity model) {

        required(model.getTenantId(),"租户ID");
        required(model.getUseStatus(),"账户状态");
        required(model.getUsername(),"用户名");
        required(model.getPassword(),"用户密码");

        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUsername, model.getUsername());
        if (!authUserService.list(wrapper).isEmpty()) {
            addError("用户名["+model.getUsername()+"]已被使用");
        }

        return this;
    }

    @Override
    public AuthUserBizCheck deletable(UserEntity model) {

        required(model.getUserId(),"用户ID");
        if (authUserService.getById(model.getUserId()) == null) {
            addError("用户ID:",model.getUserId(),"无效");
        }
        return this;
    }

    @Override
    public AuthUserBizCheck updatable(UserEntity model) {

        required(model.getUserId(),"用户ID");
        required(model.getTenantId(),"租户ID");
        required(model.getUseStatus(),"账户状态");
        required(model.getUsername(),"用户名");
        required(model.getPassword(),"用户密码");

        UserEntity dbEntity = authUserService.getById(model.getUserId());
        if (dbEntity == null) {
            addError("用户ID:",model.getUserId(),"无效");
            return this;
        }
        model.setUsername(dbEntity.getUsername());
        model.setCreateUser(dbEntity.getCreateUser());
        model.setCreateDate(dbEntity.getCreateDate());


        return this;
    }
}
