package com.ssfw.common.tenant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 租户ID赋值注解
 * 给bean方法或bean方法参数加上该注解后，方法参数的tenantId属性将被赋值为当前登录用户的租户ID
 * @author a
 * @date 2022/9/16
 * @since 2.7.3
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TenantAssistant {

    String DEFAULT = "tenantId";

    String name() default DEFAULT;
}
