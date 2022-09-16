package com.ssfw.common.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssfw.auth.util.LoginUserUtil;
import com.ssfw.common.framework.check.ValidStatus;
import com.ssfw.common.framework.service.CommonService;
import com.ssfw.common.log.annotation.ActionLog;
import com.ssfw.common.log.constant.ActionTypeEnum;
import com.ssfw.common.tenant.TenantAssistant;
import com.ssfw.common.util.LocalDateUtil;
import com.ssfw.common.util.ReflectUtil;

import java.io.Serializable;
import java.util.List;

/**
 * @author a
 * @date 2022/9/16
 * @since 2.7.3
 */
public abstract class CommonServiceImpl<M extends BaseMapper<E>, E> extends ServiceImpl<M, E>  implements CommonService<E>, IService<E> {

    /**
     * 取得业务验证实现
     * @return ValidStatus
     */
    abstract public ValidStatus<E> getBizCheck();

    @ActionLog(type = ActionTypeEnum.ADD)
    @TenantAssistant
    @Override
    public boolean save(E entity) {

        //业务验证
        getBizCheck().addable(entity).throwIfError();

        ReflectUtil.setValueIfAbsent(entity, "createUser", LoginUserUtil.getLoginNickname());
        ReflectUtil.setValueIfAbsent(entity, "createDate", LocalDateUtil.nowLocalDateTime());

        return super.save(entity);
    }

    @ActionLog(type = ActionTypeEnum.DELETE)
    @TenantAssistant
    @Override
    public boolean removeById(E entity) {

        //业务验证
        getBizCheck().deletable(entity).throwIfError();
        return super.removeById(entity);
    }

    @ActionLog(type = ActionTypeEnum.UPDATE)
    @TenantAssistant
    @Override
    public boolean updateById(E entity) {

        //业务验证
        getBizCheck().updatable(entity).throwIfError();

        ReflectUtil.setValueIfAbsent(entity, "updateUser", LoginUserUtil.getLoginNickname());
        ReflectUtil.setValueIfAbsent(entity, "updateDate", LocalDateUtil.nowLocalDateTime());

        return super.updateById(entity);
    }

    @ActionLog(type = ActionTypeEnum.UPDATE)
    @TenantAssistant
    @Override
    public boolean update(E entity, Wrapper<E> updateWrapper) {

        //业务验证
        getBizCheck().updatable(entity).throwIfError();

        ReflectUtil.setValueIfAbsent(entity, "updateUser", LoginUserUtil.getLoginNickname());
        ReflectUtil.setValueIfAbsent(entity, "updateDate", LocalDateUtil.nowLocalDateTime());

        return super.update(entity, updateWrapper);
    }

    @Override
    public E getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public long count(Wrapper<E> queryWrapper) {
        return super.count(queryWrapper);
    }

    @Override
    public List<E> list(Wrapper<E> queryWrapper) {
        return super.list(queryWrapper);
    }

    @Override
    public <E1 extends IPage<E>> E1 page(E1 page, Wrapper<E> queryWrapper) {
        return super.page(page, queryWrapper);
    }

    @Override
    public QueryChainWrapper<E> query() {
        return super.query();
    }

    @Override
    public LambdaQueryChainWrapper<E> lambdaQuery() {
        return super.lambdaQuery();
    }

    @Override
    public KtQueryChainWrapper<E> ktQuery() {
        return super.ktQuery();
    }
}
