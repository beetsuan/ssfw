package com.ssfw.common.log.annotation;

import com.ssfw.common.log.constant.ActionTypeEnum;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 记操作记录日志使用，用于需要记日志的方法上，使用AOP
 * @author a
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionLog {

    String DEFAULT = "";

    /**
     * 操作类型
     */
    ActionTypeEnum type();
    /**
     * 日志描述
     */
    String name() default DEFAULT;

}
