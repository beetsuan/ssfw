package com.ssfw.common.exception;

import com.ssfw.common.framework.response.ResponseVo;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author a
 */
public interface ExceptionPrintWriter {

    /**
     * 打印错误信息
     * @param map 错误信息对象
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param redirectAttributes RedirectAttributes
     * @return 错误界面路径
     * @throws IOException IO错误
     */
    Object out(ResponseVo map, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException;
}
