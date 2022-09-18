package com.ssfw.autoconfigure.security;

import com.ssfw.common.framework.response.ResponseVo;
import com.ssfw.common.util.JsonUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * json输出
 * @author beets
 */
public class OutJsonHandler {

    /**
     * 返回JSON数据
     * @param response HttpServletResponse
     * @param statusCode 状态码
     * @param message 信息
     */
    public static void print(HttpServletResponse response,int statusCode, String message) throws IOException {

        ResponseVo responseModel = ResponseVo.failure(message).setStatus(statusCode);
        responseModel.put("message", message);
        print(response,responseModel);
    }

    /**
     * 返回JSON数据
     * @param response HttpServletResponse
     * @param responseVo 信息
     */
    public static void print(HttpServletResponse response, ResponseVo responseVo) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("cache-control", "no-cache");
        PrintWriter writer = response.getWriter();
        writer.print(JsonUtil.obj2String(responseVo));
        writer.flush();
        writer.close();
    }

    /**
     * 返回JSON数据
     * @param response HttpServletResponse
     * @param statusCode 状态码
     * @param body 信息内容对象
     */
    public static void print(HttpServletResponse response,int statusCode, Object body) throws IOException {
        print(response,statusCode, null == body? null: JsonUtil.obj2String(body));
    }
}
