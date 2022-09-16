package com.ssfw.common.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记model属性
 * 用于记录日志的时候，优先级ApiModelProperty
 * @author a
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyName {

    String value() default "";
    boolean isMain() default false;
}
