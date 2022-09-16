package com.ssfw.common.framework.request;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页包装
 * @author a
 */
public interface PaginationWrapper {

    /**
     * 设置分页信息
     * @param request HttpServletRequest
     */
    void setPagination(HttpServletRequest request);
}
