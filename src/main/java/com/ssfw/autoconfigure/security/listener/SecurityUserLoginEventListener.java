package com.ssfw.autoconfigure.security.listener;

import com.ssfw.auth.mapper.UserSignLogMapper;
import com.ssfw.autoconfigure.AsyncTaskAutoConfiguration;
import com.ssfw.autoconfigure.security.event.SecurityUserLoginEvent;
import com.ssfw.auth.entity.UserSignLogEntity;
import com.ssfw.common.centext.SpringContextHolder;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 监听用户登录事件的监听器
 * @author a
 */
@Component
public class SecurityUserLoginEventListener implements ApplicationListener<SecurityUserLoginEvent>,SecuritySignLogPersistListener {

    @Override
    public void onApplicationEvent(@NotNull final SecurityUserLoginEvent event) {

        SpringContextHolder.optionalBean(SecuritySignLogPersistListener.class).ifPresent(o -> o.doLog(event));
    }

    /**
     * 异步存储登录日志
     * @param event
     */
    @Async(AsyncTaskAutoConfiguration.EXECUTOR)
    @Override
    public void doLog(SecurityUserLoginEvent event){

        UserSignLogEntity log = event.getLog();
        //IP解析
        log.ipLookup();
        //持久胡用户登录日志
        SpringContextHolder.optionalBean(UserSignLogMapper.class).ifPresent(mapper -> mapper.insert(log));
    }
}
