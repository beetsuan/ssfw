package com.ssfw.autoconfigure.security;

import com.ssfw.auth.contant.UserConstants;
import com.ssfw.common.util.HttpSessionUtil;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 验证码获取和验证
 */
public class CaptchaWebAuthenticationDetails extends WebAuthenticationDetails {

    private boolean passed;

    public CaptchaWebAuthenticationDetails(HttpServletRequest request, boolean enableCaptcha) {
        super(request);
        request.setAttribute(UserConstants.LOGIN_USERNAME, request.getParameter(UserConstants.LOGIN_USERNAME));
        request.setAttribute(UserConstants.SESSION_TENANT_ID, request.getParameter(UserConstants.SESSION_TENANT_ID));
        if (enableCaptcha){

            String captcha = request.getParameter(UserConstants.SESSION_SECURITY_CAPTCHA);
            //登录验证码验证
            checkCaptcha(captcha);
        }else {
            passed = true;
        }
    }


    /**
     * 验证码验证
     */
    private void checkCaptcha(String captcha) {


        if (captcha == null || "".equals(captcha)) {
            return;
        }

        HttpSession session = HttpSessionUtil.getSession();
        if (null != session){
            Object sessionCaptcha = session.getAttribute(UserConstants.SESSION_SECURITY_CAPTCHA);
            session.removeAttribute(UserConstants.SESSION_SECURITY_CAPTCHA);
            if (null !=sessionCaptcha && captcha.equalsIgnoreCase(sessionCaptcha.toString())) {
                passed = true;
            }
        }
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
