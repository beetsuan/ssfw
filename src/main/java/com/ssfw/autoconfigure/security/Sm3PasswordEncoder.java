package com.ssfw.autoconfigure.security;

import com.ssfw.common.util.encrypt.Sm3Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * sm3密码加密器
 * @author beets
 */
@Slf4j
public class Sm3PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return Sm3Util.digestToBase64("ssfw^"+rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        } else if (encodedPassword != null && encodedPassword.length() != 0) {
            return encode(rawPassword).equals(encodedPassword);
        } else {
            log.warn("Empty encoded password");
            return false;
        }
    }
}
