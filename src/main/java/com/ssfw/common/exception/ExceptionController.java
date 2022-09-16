package com.ssfw.common.exception;

import com.ssfw.common.framework.response.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * Controller全局异常处理
 * @author ssfw
 */
@ControllerAdvice
public class ExceptionController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    private final ExceptionPrintWriter exceptionPrintWriter;
    private final ExceptionParser exceptionParser;


    final static String CLIENT_ABORT_EXCEPTION = "org.apache.catalina.connector.ClientAbortException";

    public ExceptionController(ExceptionPrintWriter exceptionPrintWriter, ExceptionParser exceptionParser) {
        this.exceptionPrintWriter = exceptionPrintWriter;
        this.exceptionParser = exceptionParser;
    }

    /**
     * 方法参数验证失败 和 方法参数绑定失败
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Object handleMethodArgumentNotValidException(Exception e,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {


        ResponseVo map;
        int paramFailCode = 1002;
        //先解析为可以安全输出给用户的错误信息
        map = exceptionParser.parseToSafetyErrorText(e);
        if (null == map.getErrMsg()){
            map = exceptionParser.parse(e);

        }
        map.setStatus(paramFailCode);
        try {
            return exceptionPrintWriter.out(map,request,response,redirectAttributes);
        } catch (Exception ex) {
            log.error("handleMethodArgumentNotValidException",ex);
        }
        return null;
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class,MissingServletRequestParameterException.class})
    public Object webRequestErrorHandler(Exception e,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){

        ResponseVo map ;
        int paramFailCode = 1003;

        //先解析为可以安全输出给用户的错误信息
        map = exceptionParser.parseToSafetyErrorText(e);
        if (null == map.getErrMsg()){
            map = exceptionParser.parse(e);
        }
        map.setStatus(paramFailCode);
        try {
            return exceptionPrintWriter.out(map,request,response,redirectAttributes);
        } catch (Exception exception) {
            log.error("webRequestErrorHandler",exception);
        }
        return null;
    }

    @ExceptionHandler(value = {Exception.class})
    public Object globalErrorHandler(Exception exception,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){

        if (CLIENT_ABORT_EXCEPTION.equals(exception.getClass().getName())){
            //客户端中止链接，不管
            return null;
        }

        final boolean isClearEx = exception instanceof ClearException;
        if (!isClearEx){
            //日志文件记录错误信息
            log.error("全局异常拦截",exception);
        }
        try {

            //先解析为可以安全输出给用户的错误信息
            ResponseVo map = exceptionParser.parseToSafetyErrorText(exception);
            if (null == map.getErrMsg()){
                //没有信息可以安全给到用户
                map = exceptionParser.parse(exception);
                map.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return exceptionPrintWriter.out(map,request,response,redirectAttributes);
        } catch (Exception e) {
            log.error("globalErrorHandler",exception);
        }
        return null;

    }


}
