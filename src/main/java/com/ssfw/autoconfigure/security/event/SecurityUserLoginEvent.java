package com.ssfw.autoconfigure.security.event;

import com.ssfw.auth.entity.UserSignLogEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户登录成功事件
 * @author a
 */
@Getter
public class SecurityUserLoginEvent extends ApplicationEvent {

    private final UserSignLogEntity log;

    public SecurityUserLoginEvent(Object source, UserSignLogEntity log) {
        super(source);
        this.log = log;
    }

}
