package com.ssfw.autoconfigure.security.listener;

import com.ssfw.autoconfigure.security.event.SecurityUserLoginEvent;

/**
 * 登录日志持久化监听器
 */
public interface SecuritySignLogPersistListener {

    /**
     * 存储登录日志
     * @param event 登录事件
     */
    void doLog(SecurityUserLoginEvent event);
}
