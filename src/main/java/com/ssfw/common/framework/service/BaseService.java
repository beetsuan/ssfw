package com.ssfw.common.framework.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.ChainQuery;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.ChainUpdate;
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper;
import java.io.Serializable;
import java.util.List;

/**
 * 通用的的服务接口
 * 持久化接口已实现日志记录和调用业务验证接口
 * @author a
 * @date 2022/9/16
 * @since 2.7.3
 */
public interface BaseService<E> {

    /**
     * 插入一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     * @return 成功与否
     */
     boolean save(E entity);

    /**
     * 根据实体(ID)删除
     *
     * @param entity 实体
     * @return 成功与否
     */
     boolean removeById(E entity);

    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     * @return 成功与否
     */
     boolean updateById(E entity);

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     * @return 对应实体
     */
    E getById(Serializable id);

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return 记录数
     */
    long count(Wrapper<E> queryWrapper);

    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return 实体集合
     */
    List<E> list(Wrapper<E> queryWrapper);

    /**
     * 翻页查询
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return IPage对象
     */
    <E1 extends IPage<E>> E1 page(E1 page, Wrapper<E> queryWrapper);


    /**
     * 以下的方法使用介绍:
     *
     * 一. 名称介绍
     * 1. 方法名带有 query 的为对数据的查询操作, 方法名带有 update 的为对数据的修改操作
     * 2. 方法名带有 lambda 的为内部方法入参 column 支持函数式的
     * 二. 支持介绍
     *
     * 1. 方法名带有 query 的支持以 {@link ChainQuery} 内部的方法名结尾进行数据查询操作
     * 2. 方法名带有 update 的支持以 {@link ChainUpdate} 内部的方法名为结尾进行数据修改操作
     *
     * 三. 使用示例,只用不带 lambda 的方法各展示一个例子,其他类推
     * 1. 根据条件获取一条数据: `query().eq("column", value).one()`
     * 2. 根据条件删除一条数据: `update().eq("column", value).remove()`
     *
     */

    /**
     * 链式查询 普通
     *
     * @return QueryWrapper 的包装类
     */
    QueryChainWrapper<E> query();

    /**
     * 链式查询 lambda 式
     * <p>注意：不支持 Kotlin </p>
     *
     * @return LambdaQueryWrapper 的包装类
     */
    LambdaQueryChainWrapper<E> lambdaQuery();

    /**
     * 链式查询 lambda 式
     * kotlin 使用
     *
     * @return KtQueryWrapper 的包装类
     */
    KtQueryChainWrapper<E> ktQuery();

}
