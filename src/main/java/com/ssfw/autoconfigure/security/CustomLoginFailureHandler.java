package com.ssfw.autoconfigure.security;

import com.ssfw.auth.contant.UserConstants;
import com.ssfw.auth.entity.UserSignLogEntity;
import com.ssfw.autoconfigure.CustomSecurityProperties;
import com.ssfw.autoconfigure.security.event.SecurityUserLoginEvent;
import com.ssfw.common.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录失败的处理程序
 */
@Slf4j
public class CustomLoginFailureHandler implements AuthenticationFailureHandler, ApplicationEventPublisherAware {

    private final CustomSecurityProperties securityProperties;

    private ApplicationEventPublisher applicationEventPublisher;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public CustomLoginFailureHandler(CustomSecurityProperties securityProperties) {

        this.securityProperties = securityProperties;
    }


    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, HttpServletResponse response, AuthenticationException exception){


        final String message = exception.getMessage();
        try {

            //登录日志
            UserSignLogEntity signLog = UserSignLogEntity.login(request);
            signLog.setSessionId("0");
            signLog.setUserId(null);
            signLog.setFailureReason(message);
            signLog.setIsFailed(1);
            signLog.setUsername(request.getAttribute(UserConstants.LOGIN_USERNAME)+"");
            Object tenantId = request.getAttribute(UserConstants.SESSION_TENANT_ID);
            if (null!=tenantId){
                signLog.setTenantId((Integer) tenantId);
            }else{
                signLog.setTenantId(0);
            }
            //发布用户登录失败事件
            applicationEventPublisher.publishEvent(new SecurityUserLoginEvent(this, signLog));

            if (HttpUtil.isAjaxRequest(request)) {
                // AJAX请求
                int statusCode = 401;
                OutJsonHandler.print(response,statusCode, message);
            } else if (!response.isCommitted()) {
                // 非AJAX请求，跳转登录页
                request.getSession(true).setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, message);
                redirectStrategy.sendRedirect(request,response,securityProperties.getLogoutSuccessUrl());
            }
        }catch (Exception e){
            log.error("CustomLoginFailureHandler Error:{}",e.getMessage());
            log.debug("CustomLoginFailureHandler Error",e);
        }
    }


    @Override
    public void setApplicationEventPublisher(@NotNull ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
