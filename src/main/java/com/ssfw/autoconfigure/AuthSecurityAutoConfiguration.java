package com.ssfw.autoconfigure;

import com.ssfw.auth.mapper.AuthUserRoleMapper;
import com.ssfw.auth.service.AuthUserService;
import com.ssfw.auth.service.impl.UserDetailsServiceImpl;
import com.ssfw.autoconfigure.security.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import javax.servlet.http.HttpServletRequest;

/**
 * spring security自动配置
 * @author a
 * @date 2022/9/16
 * @since 2.7.3
 */
@AutoConfiguration
@EnableWebSecurity
@EnableConfigurationProperties(CustomSecurityProperties.class)
public class AuthSecurityAutoConfiguration{


    @Bean
    public SecurityFilterChain spAuthSecurityFilterChain(HttpSecurity http
                                                         ,CustomSecurityProperties securityProperties
                                                         ,CustomLoginFailureHandler loginFailureHandler
                                                         ,CustomLoginSuccessHandler loginSuccessHandler
                                                         ,AuthenticationDetailsSource<HttpServletRequest,  CaptchaWebAuthenticationDetails> authenticationDetailsSource
        ) throws Exception {


        http.formLogin().loginPage(securityProperties.getLoginSuccessUrl())
                .authenticationDetailsSource(authenticationDetailsSource)

                //登录成功的处理程序
                .successHandler(loginSuccessHandler)
                //登录失败的处理程序
                .failureHandler(loginFailureHandler)
                .and().logout()
                .and().authorizeRequests()
                .antMatchers(securityProperties.getLoginSuccessUrl(),"/security/**").permitAll()
                .antMatchers("/admin/**","do/**").hasRole("admin")
                .anyRequest().authenticated()
                .and().csrf().disable();

        return http.build();
    }

    /**
     * 配置地址栏不能识别 // 的情况
     * @return HttpFirewall
     */
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        //此处可添加别的规则,目前只设置 允许双 //
        firewall.setAllowUrlEncodedDoubleSlash(true);
        return firewall;
    }

    @Bean
    @ConditionalOnMissingBean
    public CustomLoginFailureHandler loginFailureHandler(CustomSecurityProperties securityProperties){
        return new CustomLoginFailureHandler(securityProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public CustomLoginSuccessHandler loginSuccessHandler(CustomSecurityProperties securityProperties){
        return new CustomLoginSuccessHandler(securityProperties);
    }


    /**
     * 鉴权用户信息服务
     * @param authUserService 用户信息服务
     * @param authUserRoleMapper 用户持久化mapper
     * @return UserDetailsService
     */
    @Bean
    UserDetailsService customUserService(AuthUserService authUserService, AuthUserRoleMapper authUserRoleMapper) {
        return new UserDetailsServiceImpl(authUserService, authUserRoleMapper);
    }


    /**
     * 密码加密策略
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Sm3PasswordEncoder();
    }



    /**
     * 授权明细资源类
     * @return CustomAuthenticationDetailsSource
     */
    @Bean
    public AuthenticationDetailsSource<HttpServletRequest, CaptchaWebAuthenticationDetails> authenticationDetailsSource(CustomSecurityProperties securityProperties){
        return new CustomAuthenticationDetailsSource(securityProperties);
    }

    /**
     * 登录处理程序
     * @param userDetailsService 鉴权用户信息服务
     * @param passwordEncoder 密码加密策略
     * @return AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        CustomAuthenticationProvider provider = new CustomAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider) {

        ProviderManager authenticationManager = new ProviderManager(authenticationProvider);
        //不擦除认证密码，擦除会导致TokenBasedRememberMeServices因为找不到Credentials再调用UserDetailsService而抛出UsernameNotFoundException
        authenticationManager.setEraseCredentialsAfterAuthentication(false);
        return authenticationManager;
    }
}
