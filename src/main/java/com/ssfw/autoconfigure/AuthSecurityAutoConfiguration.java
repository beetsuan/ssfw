package com.ssfw.autoconfigure;

import com.ssfw.auth.mapper.AuthUserRoleMapper;
import com.ssfw.auth.service.AuthUserService;
import com.ssfw.auth.service.impl.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/**
 * @author a
 * @date 2022/9/16
 * @since 2.7.3
 */
@AutoConfiguration
@EnableWebSecurity
public class AuthSecurityAutoConfiguration{

    @Bean
    UserDetailsService customUserService(AuthUserService authUserService, AuthUserRoleMapper authUserRoleMapper) {
        return new UserDetailsServiceImpl(authUserService, authUserRoleMapper);
    }

    @Bean
    public SecurityFilterChain spAuthSecurityFilterChain(HttpSecurity http) throws Exception {


        http.formLogin().loginPage("/login")
                .and().logout()
                .and().authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/admin/**","do/auth/**").hasRole("admin")
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

    /**
     * 密码加密策略
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
