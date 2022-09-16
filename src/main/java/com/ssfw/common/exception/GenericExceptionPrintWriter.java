package com.ssfw.common.exception;

import com.ssfw.common.framework.response.ResponseVo;
import com.ssfw.common.util.HttpUtil;
import com.ssfw.common.util.JsonUtil;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author a
 */
public class GenericExceptionPrintWriter implements ExceptionPrintWriter {

    @Override
    public Object out(ResponseVo map, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {

        if (HttpUtil.isAjaxRequest(request)) {
            // AJAX请求
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType("application/json");
            response.setHeader("cache-control", "no-cache");
            PrintWriter writer = response.getWriter();
            writer.print(JsonUtil.obj2String(map));
            writer.flush();
        } else if (!response.isCommitted()) {
            // 非AJAX请求,捕获异常后进行重定向500页面
            for (String key : map.keySet()) {
                redirectAttributes.addFlashAttribute(key,map.get(key));
                request.setAttribute(key,map.get(key));
            }
            return "/error/500.html";
        }
        return null;
    }
}
