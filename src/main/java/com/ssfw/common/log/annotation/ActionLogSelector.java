package com.ssfw.common.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 记操作记录日志使用，用于选择字段
 * @author a
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionLogSelector {

    /**
     * 在忽略@ActionLogIgnore的前提下，记录所有字段，默认否
     * 优先级 第一
     */
    boolean all() default false;

    /**
     * 在忽略@ActionLogIgnore的前提下，只记录有@PropertyName注解的字段，默认是
     * 优先级 第二
     */
    boolean onlyPropertyNameField() default true;

}
