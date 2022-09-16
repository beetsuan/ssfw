package com.ssfw.autoconfigure.security.event;

import com.ssfw.auth.dto.CustomUserDetails;
import com.ssfw.auth.entity.UserSignLogEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录成功事件
 * @author a
 */
@Getter
public class SecurityUserLoginEvent extends ApplicationEvent {

    private final UserSignLogEntity log;

    public SecurityUserLoginEvent(Object source, HttpServletRequest request, CustomUserDetails principal, AuthenticationException exception) {
        super(source);
        log = UserSignLogEntity.login(request);
        if (null!=exception){
            log.setIsFailed(1);
            log.setFailureReason(exception.getLocalizedMessage());
        }
        log.setSessionId(request.getSession(true).getId());
        log.setUserId(principal.getId());
        log.setUsername(principal.getUsername());
        log.setTenantId(principal.getTenantId());
    }

    public SecurityUserLoginEvent(Object source, HttpServletRequest request, CustomUserDetails principal) {
        this(source, request, principal, null);
    }

}
