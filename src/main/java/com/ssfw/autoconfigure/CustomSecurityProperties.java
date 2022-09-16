
package com.ssfw.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = CustomSecurityProperties.KEY)
@Setter
@Getter
public class CustomSecurityProperties {


    public final static String KEY = "spring.security.custom";

    private boolean captcha = true;

    private String logoutSuccessUrl = "";

    private String loginSuccessUrl = "/login";

}
