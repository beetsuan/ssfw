package com.ssfw.common.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置Entity类的名称，所属应用
 * @author a
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityName {

    String value() default "";
    String appId() default "";
    String modelId() default "";
}
