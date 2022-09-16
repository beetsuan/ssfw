package com.ssfw.common.exception;

import com.ssfw.common.framework.response.ResponseVo;
import com.ssfw.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.util.unit.DataSize;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * @author a
 */
public class GenericExceptionParser implements ExceptionParser {

    @Override
    public String parseToJson(Exception e){
        return JsonUtil.obj2String(parse(e));
    }

    private static final Logger log = LoggerFactory.getLogger(GenericExceptionParser.class);

    @Override
    public ResponseVo parseToSafetyErrorText(Exception e){

        String message = null;
        if (e instanceof ClearException){
            //清楚的异常信息
            message = e.getMessage();
            if (null == message){
                message = e.toString();
            }
            ResponseVo responseModel = ResponseVo.failure(message);
            responseModel.setStatus(HttpStatus.OK.value());
            String messageCode = ((ClearException) e).getMessageCode();
            if (null!=messageCode){
                return responseModel.setTips(messageCode);
            }
            return responseModel;
        }

        if (e instanceof DuplicateKeyException) {
            message = "响应失败,主键或唯一键重复使用";
        }else if (e instanceof BindException || e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult;
            if (e instanceof BindException){
                bindingResult = ((BindException) e).getBindingResult();
            }else{
                bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            }
            if (null != bindingResult){
                FieldError error = bindingResult.getFieldError();
                if (null != error){
                    final String em = error.getDefaultMessage();
                    String field = error.getField();
                    if (null!=em && em.length() <= 100) {
                        message = "["+field+"] "+em;
                    }else{
                        message = "请求参数格式有误，参数：" + field + "，值：" + error.getRejectedValue();
                    }
                }
            }
            else{
                message = "请求参数格式有误";
            }
        }else if (e instanceof HttpRequestMethodNotSupportedException) {
            message = "不支持的请求类型："+((HttpRequestMethodNotSupportedException) e).getMethod();
        }else if (e instanceof MissingServletRequestParameterException) {
            message = "未提供请求参数："+((MissingServletRequestParameterException) e).getParameterName();
        }else if (e instanceof MaxUploadSizeExceededException){
            message = "上传的文件大小超限："+  DataSize.ofMegabytes(((MaxUploadSizeExceededException)e).getMaxUploadSize()).toMegabytes()+"MB";
        }else if (e instanceof ValidationException) {
            message = "参数格式验证不通过,Params validate failed";
            log.debug("参数格式验证不通过,{}",e.getMessage());
        }
        if (null!=message){
            ResponseVo responseModel = ResponseVo.failure(message.endsWith("\\.") ? message : message.concat("."));
            responseModel.setStatus(HttpStatus.OK.value());
            return responseModel;
        }
        return ResponseVo.empty();
    }

    @Override
    public ResponseVo parse(Exception e) {

        ResponseVo responseModel = ResponseVo.empty();
        responseModel.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        String msg = "";
        Throwable cause = e.getCause();
        msg += null==e.getMessage()?e.toString():e.getMessage();
        String message = null;
        if (null!= cause){
            message = cause.getMessage();
            responseModel.setErrDetail(message);
        }
        if (e instanceof NullPointerException) {
            msg = "操作失败,程序出现空指针异常";
            responseModel.setErrDetail(null);
        }else if (e instanceof ArithmeticException) {
            msg = "操作失败,运算条件错误:ArithmeticException:"+msg;
        }else if (e instanceof ConstraintViolationException) {
            msg = "操作失败,数据关系冲突,Constraint Violation";
        }else if (e instanceof DataAccessException) {
            msg="操作失败,持久层执行抛出错误";
            if (null!= message){
                if (message.contains("atomikos")){
                    msg = "操作失败,连接数据源失败";
                    responseModel.setErrDetail(null);
                }else{
                    msg+= message;
                }
            }
        }
        responseModel.setErrMsg(msg.endsWith("\\.")?msg:msg.concat("."));
        return responseModel;
    }
}
