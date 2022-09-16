package com.ssfw.common.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author beets
 */
public class HttpSessionUtil {


    public static HttpServletRequest getRequest(){

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static HttpSession getSession(){

        HttpServletRequest request = getRequest();
        return request.getSession(false);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAttribute(String name){

        HttpSession session = getSession();
        if (null != session){
            return (T) session.getAttribute(name);
        }
        return null;
    }

    public static void setAttribute(String name,Object value){

        HttpSession session = getSession();
        if (null != session){
            session.setAttribute(name, value);
        }
        throw new RuntimeException("session is null");
    }


    public static void invalidate() {
        HttpSession session = getSession();
        if (session!=null){
            session.invalidate();
        }
    }
}
