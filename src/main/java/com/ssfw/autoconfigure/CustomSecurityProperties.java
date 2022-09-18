
package com.ssfw.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * spring security 配置项
 * @author beets
 */
@ConfigurationProperties(prefix = CustomSecurityProperties.KEY)
@Setter
@Getter
public class CustomSecurityProperties {


    public final static String KEY = "spring.security.custom";

    /**
     * 是否开启验证码
     */
    private boolean captcha = false;

    private String logoutSuccessUrl = "";

    private String loginSuccessUrl = "/login";

}
