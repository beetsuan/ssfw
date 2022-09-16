package com.ssfw.autoconfigure.security.listener;

import com.ssfw.auth.mapper.UserSignLogMapper;
import com.ssfw.autoconfigure.security.event.SecurityUserLoginEvent;
import com.ssfw.auth.entity.UserSignLogEntity;
import com.ssfw.common.centext.SpringContextHolder;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 监听用户登录成功事件
 * @author a
 */
@Component
public class SecurityUserLoginEventListener implements ApplicationListener<SecurityUserLoginEvent> {

    @Override
    public void onApplicationEvent(SecurityUserLoginEvent event) {

        UserSignLogEntity log = event.getLog();
        //IP解析
        log.ipLookup();
        //记录登入成功日志
        SpringContextHolder.optionalBean(UserSignLogMapper.class).ifPresent(mapper -> mapper.insert(log));
    }
}
