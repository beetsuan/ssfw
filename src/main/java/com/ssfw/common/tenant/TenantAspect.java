package com.ssfw.common.tenant;

import com.ssfw.auth.util.LoginUserUtil;
import com.ssfw.common.framework.entity.TenantFacade;
import com.ssfw.common.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import java.lang.annotation.Annotation;

/**
 * 记操作记录日志使用，AOP实现类，基于@ActionLog来实现日志记录功能
 * @author a
 */
@Aspect
@Slf4j
public class TenantAspect {

    /**
     * 定义切点 @Pointcut
     */
    @Pointcut("@annotation(com.ssfw.common.tenant.TenantAssistant)")
    public void tenantAssistantAspect(){
    }

    private Argument getArg(JoinPoint joinPoint,MethodSignature signature){

        final Object[] args = joinPoint.getArgs();
        if (null == args || args.length == 0){
            log.error("方法无参，日志记录失败");
            return null;
        }
        final Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            int paramIndex= ArrayUtils.indexOf(parameterAnnotations, parameterAnnotation);
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof TenantAssistant){
                    String name = ((TenantAssistant) annotation).name();
                    return new Argument(args[paramIndex], name);
                }
            }
        }
        return new Argument(args[0], null);
    }

    @Before("tenantAssistantAspect()")
    public void doBefore(JoinPoint joinPoint){


        // 从切面织入点处通过反射机制获取织入点处的方法
         MethodSignature signature = (MethodSignature) joinPoint.getSignature();

         //对应多个参数时，指定@TenantAssistant注解的参数被当为记日志用的参数
        Argument argument = getArg(joinPoint,signature);
        if (null == argument){
            return;
        }
        Object arg = argument.argObject;

        if (arg instanceof TenantFacade){
            ((TenantFacade<?>) arg).assignTenant();
            return;
        }

        String fieldName = argument.tenantIdFieldName == null ? TenantAssistant.DEFAULT : argument.tenantIdFieldName;
        ReflectUtil.setValueIfAbsent(arg, fieldName, LoginUserUtil.getTenantId());
    }

    static class Argument{
        final Object argObject;
        final String tenantIdFieldName;

        public Argument(Object value, String tenantIdFieldName) {
            this.argObject = value;
            this.tenantIdFieldName = tenantIdFieldName;
        }
    }
}
