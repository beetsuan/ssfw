package com.ssfw.common.exception;


import com.ssfw.common.centext.SpringContextHolder;
import com.ssfw.common.framework.response.ResponseVo;

/**
 * 异常解析器
 * @author a
 */
public interface ExceptionParser {

    /**
     * 将异常解析为json格式的内容
     * @param e Exception
     * @return json
     */
    String parseToJson(Exception e);

    /**
     * 解析异常为ResponseModel对象，过滤敏感信息
     * @param e Exception
     * @return com.inmr.core.framework.model.ResponseModel
     */
    ResponseVo parseToSafetyErrorText(Exception e);

    /**
     * 解析错误为ResponseModel对象
     * @param e Exception
     * @return com.inmr.core.framework.model.ResponseModel
     */
    ResponseVo parse(Exception e);

    /**
     * 获取异常解析器
     *
     * @return SpExceptionParser
     */
    static ExceptionParser getInstance(){
        return SpringContextHolder.getBean(ExceptionParser.class);
    }
}
