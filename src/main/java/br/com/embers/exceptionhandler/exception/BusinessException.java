package br.com.embers.exceptionhandler.exception;

public class BusinessException extends RuntimeException {

    private String message;
    private String messageKey;
    private String errorCode;
    private String title;
    private int statusCode;

    public BusinessException() {
        super();
    }

    public BusinessException(String message, String messageKey, String errorCode, String title, int statusCode) {
        this.message = message;
        this.messageKey = messageKey;
        this.errorCode = errorCode;
        this.title = title;
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getTitle() {
        return title;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
