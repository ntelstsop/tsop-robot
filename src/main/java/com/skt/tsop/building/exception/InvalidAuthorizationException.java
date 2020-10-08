package com.skt.tsop.building.exception;

import com.skt.tsop.building.model.ResponseCode;

/**
 * Authorization이 유효하지 않을 때 발생하는 예외.
 *
 * @author yjkim@ntels.com
 */
public class InvalidAuthorizationException extends CommonException {

    private static final long serialVersionUID = 8919843687392588232L;

    /**
     * 에러 코드.
     */
    private String errorCode = ResponseCode.AccessDenied.getValue();

    /**
     * 에러 메시지.
     */
    private String errorMessage = this.getMessage();

    /**
     * 기본 생성자.
     */
    public InvalidAuthorizationException() {
        super("Invalid Authorization!");
    }

    /**
     * 예외 문자열을 받는 생성자.
     *
     * @param message 예외 문자열
     */
    public InvalidAuthorizationException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
