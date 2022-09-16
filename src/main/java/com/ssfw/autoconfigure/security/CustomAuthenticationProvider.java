package com.ssfw.autoconfigure.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * 自定义登录处理程序
 * @author a
 * @date 2022/9/16
 * @since 2.7.3
 */
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        CaptchaWebAuthenticationDetails details = (CaptchaWebAuthenticationDetails) authentication.getDetails();
        if (!details.isPassed()){
            //验证不通过
            throw new BadCredentialsException("验证码输入有误");
        }
        super.additionalAuthenticationChecks(userDetails, authentication);
    }

    public static void main(String[] args) {
        String pass = "000000";
        Sm3PasswordEncoder passwordEncoder = new Sm3PasswordEncoder();
        String hashPass = passwordEncoder.encode(pass);
        System.out.println(hashPass);

        boolean f = passwordEncoder.matches(pass,hashPass);
        System.out.println(f);
    }
}
