package com.ssfw.common.exception;

/**
 * 业务异常，支持回滚事务，配置不打印到log日志文件中
 * @author a
 */
public class BusinessException extends ClearException{
    private static final long serialVersionUID = -3238900619064833035L;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public static void throwError(Object... message){

        throw new BusinessException(buildErrorMessage(message).toString());
    }

    public static void throwErrorWithCode(Object message,String messageCode){

        BusinessException exception = new BusinessException(buildErrorMessage(message).toString());
        exception.setMessageCode(messageCode);
        throw exception;
    }
}
