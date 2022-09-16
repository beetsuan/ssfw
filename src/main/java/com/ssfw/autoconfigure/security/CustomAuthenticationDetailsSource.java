package com.ssfw.autoconfigure.security;

import com.ssfw.autoconfigure.CustomSecurityProperties;
import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides a {@link org.springframework.security.core.Authentication#getDetails()} object
 * for a given web request.
 *
 * @author ssfw
 */
public class CustomAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, CaptchaWebAuthenticationDetails> {

    private final CustomSecurityProperties securityProperties;


    public CustomAuthenticationDetailsSource(CustomSecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public CaptchaWebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new CaptchaWebAuthenticationDetails(context, securityProperties.isCaptcha());
    }
}
