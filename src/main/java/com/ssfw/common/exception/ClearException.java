package com.ssfw.common.exception;

/**
 * 清晰的异常，支持回滚事务，配置不打印到log日志文件中
 * @author a
 */
public class ClearException extends RuntimeException{
    private static final long serialVersionUID = -5738900619064837519L;

    private String messageCode;

    public ClearException() {
        super();
    }

    public ClearException(String message) {
        super(message);
    }


    protected static StringBuilder buildErrorMessage(Object... message){

        StringBuilder sb = new StringBuilder();
        if (message != null) {
            for (Object o : message) {
                sb.append(o);
            }
        }
        return sb;
    }

    public static void throwError(Object... message){

       throw new ClearException(buildErrorMessage(message).toString());
    }

    public static void throwErrorWithCode(Object message,String messageCode){

        ClearException exception = new ClearException(buildErrorMessage(message).toString());
        exception.messageCode = messageCode;
        throw exception;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }
}
