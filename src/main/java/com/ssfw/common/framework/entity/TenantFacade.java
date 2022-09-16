package com.ssfw.common.framework.entity;

import com.ssfw.auth.util.LoginUserUtil;
import com.ssfw.common.util.ReflectUtil;

/**
 * 租户约定
 * @author a
 * @date 2022/9/16
 * @since 2.7.3
 */
public interface TenantFacade<E> {


    /**
     * 设置租户ID
     * @param tenantId 租户ID
     * @return this object
     */
    E setTenantId(Integer tenantId);

    /**
     * 获取租户ID
     * @return 租户ID
     */
    Integer getTenantId();

    /**
     * 反射赋值tenantId
     * @param o 对象
     */
    default void assignTenant(){
        if (null == getTenantId()){
            setTenantId(LoginUserUtil.getTenantId());
        }
    }
}
