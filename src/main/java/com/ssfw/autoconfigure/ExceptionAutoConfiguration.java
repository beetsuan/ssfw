package com.ssfw.autoconfigure;

import com.ssfw.common.exception.ExceptionPrintWriter;
import com.ssfw.common.exception.GenericExceptionParser;
import com.ssfw.common.exception.ExceptionParser;
import com.ssfw.common.exception.GenericExceptionPrintWriter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author a
 * @date 2022/9/14
 * @since 2.7.3
 */
@AutoConfiguration
public class ExceptionAutoConfiguration {

    /**
     *  //@ConditionalOnMissingBean(value = ExceptionOutput.class)
     *  该注解的意思是： 如果Ioc容器中没有 spExceptionPrintWriter 类型的 bean时
     *  就将GenericSpExceptionPrintWriter注册到Ioc容器中
     */
    @Bean
    @ConditionalOnMissingBean(value = ExceptionParser.class)
    public ExceptionParser exceptionParser() {
        return new GenericExceptionParser();
    }

    @Bean
    @ConditionalOnMissingBean(value = ExceptionPrintWriter.class)
    public ExceptionPrintWriter spExceptionPrintWriter() {
        return new GenericExceptionPrintWriter();
    }
}

