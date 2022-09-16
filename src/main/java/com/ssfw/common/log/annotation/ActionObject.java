package com.ssfw.common.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 记操作记录日志使用，用于需要记日志的方法参数上，表示被注解的参数才是记日志用到的参数，否则默认第一个参数为记日志用的参数
 * @author a
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionObject {

}
