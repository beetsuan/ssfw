package com.ssfw.autoconfigure.security;

import com.ssfw.auth.contant.UserConstants;
import com.ssfw.auth.dto.CustomUserDetails;
import com.ssfw.autoconfigure.CustomSecurityProperties;
import com.ssfw.autoconfigure.security.event.SecurityUserLoginEvent;
import com.ssfw.common.framework.response.ResponseVo;
import com.ssfw.auth.entity.UserSignLogEntity;
import com.ssfw.common.util.HttpUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录成功的处理程序
 */
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements ApplicationEventPublisherAware {

    private final Logger log = LoggerFactory.getLogger(CustomLoginSuccessHandler.class);

    private ApplicationEventPublisher applicationEventPublisher;

    private RequestCache requestCache = new HttpSessionRequestCache();

    private final CustomSecurityProperties securityProperties;


    @Autowired
    public CustomLoginSuccessHandler(CustomSecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();

        log.debug("USER : {} LOGIN SUCCESS !  ", username);

        CustomUserDetails principal = (CustomUserDetails)authentication.getPrincipal();

        HttpSession session = request.getSession(true);
        session.setAttribute(UserConstants.SESSION_USER_ID, principal.getId());
        session.setAttribute(UserConstants.SESSION_USER_NAME, principal.getUsername());
        session.setAttribute(UserConstants.SESSION_NICK_NAME, principal.getNickname());
        session.setAttribute(UserConstants.SESSION_TENANT_ID, principal.getTenantId());

        UserSignLogEntity signLog = UserSignLogEntity.login(request);
        signLog.setSessionId(session.getId());
        signLog.setUserId(principal.getId());
        signLog.setUsername(username);
        signLog.setTenantId(principal.getTenantId());
        //发布用户登录成功事件
        applicationEventPublisher.publishEvent(new SecurityUserLoginEvent(this, signLog));


        if (doResponse(request, response, session, principal)) {
            super.clearAuthenticationAttributes(request);
            return;
        }
        super.onAuthenticationSuccess(request, response, authentication);

    }

    /**
     * 响应登录请求
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param session HttpSession
     * @param user 用户信息
     * @return 是否为ajax请求
     */
    private boolean doResponse(HttpServletRequest request, HttpServletResponse response, HttpSession session, CustomUserDetails user) throws IOException {


        SavedRequest savedRequest;

        String loginSuccessUrl = securityProperties.getLoginSuccessUrl();


        String redirectUrl = loginSuccessUrl;

        //重新回到登录前的页面信息
        savedRequest =  this.requestCache.getRequest(request, response);
        if (null!=savedRequest){
            redirectUrl = savedRequest.getRedirectUrl();
        }else {
            Object sessionSavedRequest = session.getAttribute("SP_SECURITY_SAVED_REQUEST");
            if (null != sessionSavedRequest){
                savedRequest = (SavedRequest) sessionSavedRequest;
                redirectUrl = savedRequest.getRedirectUrl();
            }
        }
        if (null != savedRequest){
            this.requestCache.removeRequest(request, response);
        }

        if (null!=redirectUrl){
            //有可能登录之前的url请求是lookup的，这是获取用户登录密码前端加密盐使用的，不能重定向到该url上去
            String urlLookup = "/lookup";
            if (redirectUrl.contains(urlLookup)){
                redirectUrl = loginSuccessUrl;
            }
            //这是获取验证码，登录登出等路径
            else {
                String urlSecurity = "/security/";
                if (redirectUrl.contains(urlSecurity)){
                    redirectUrl = loginSuccessUrl;
                }
            }
        }


        //响应请求
        if (HttpUtil.isAjaxRequest(request)) {

            if (!"".equals(request.getContextPath())){
                redirectUrl = request.getContextPath()+loginSuccessUrl;
            }
            // AJAX请求
            int statusCode = 1;
            ResponseVo vo = ResponseVo.empty();
            vo.put("text","logged in");
            vo.put("page",redirectUrl);
            vo.put("status",statusCode);
            ResponseVo userVo = ResponseVo.empty();
            vo.put("user", userVo);
            if (null != user){
                userVo.put("userId", user.getId());
                userVo.put("userRoles", user.getRoles());
                userVo.put("username", user.getUsername());
                userVo.put("nickname", user.getNickname());
                userVo.put("tenantId", user.getTenantId());

            }
            OutJsonHandler.print(response,vo);

            return true;
        }

        //跳转
        if (null != savedRequest){

            //savedRequest
            String targetUrlParameter = this.getTargetUrlParameter();
            if (!this.isAlwaysUseDefaultTargetUrl() && (targetUrlParameter == null || !StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
                this.clearAuthenticationAttributes(request);
                this.logger.debug("Redirecting to DefaultSavedRequest Url: " + redirectUrl);
                this.getRedirectStrategy().sendRedirect(request, response, redirectUrl);
                return true;
            }
        }
        return false;
    }

    @Override
    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    @Override
    public void setApplicationEventPublisher(@NotNull ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
