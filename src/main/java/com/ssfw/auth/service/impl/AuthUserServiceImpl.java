package com.ssfw.auth.service.impl;

import com.ssfw.auth.bizcheck.AuthUserBizCheck;
import com.ssfw.auth.entity.UserEntity;
import com.ssfw.auth.mapper.AuthUserMapper;
import com.ssfw.auth.service.AuthUserService;
import com.ssfw.common.framework.check.ValidStatus;
import com.ssfw.common.framework.service.impl.CommonServiceImpl;
import com.ssfw.common.log.annotation.ActionLog;
import com.ssfw.common.log.constant.ActionTypeEnum;
import com.ssfw.common.tenant.TenantAssistant;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author ssfw
 * @since 2022-09-14
 */
@Service
public class AuthUserServiceImpl extends CommonServiceImpl<AuthUserMapper, UserEntity> implements AuthUserService {


    @Override
    public ValidStatus<UserEntity> getBizCheck() {
        return new AuthUserBizCheck(this);
    }

    @ActionLog(type = ActionTypeEnum.ADD)
    @TenantAssistant
    @Override
    public boolean save(UserEntity entity) {
        return super.save(entity);
    }

    @ActionLog(type = ActionTypeEnum.CANCEL)
    @TenantAssistant
    @Override
    public boolean removeById(UserEntity entity) {

        //entity.setDeleted(1);
        return super.removeById(entity);
    }

    @ActionLog(type = ActionTypeEnum.UPDATE)
    @TenantAssistant
    @Override
    public boolean updateById(UserEntity entity) {
        return super.updateById(entity);
    }
}
