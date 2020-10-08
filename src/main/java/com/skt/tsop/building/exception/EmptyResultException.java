package com.skt.tsop.building.exception;

import com.skt.tsop.building.model.ResponseCode;

/**
 * 반드시 있어야 할 결과가 없을 때 발생되는 예외.
 *
 * @author yjkim@ntels.comY
 */
public class EmptyResultException extends CommonException {

    private static final long serialVersionUID = -7456074249580626963L;

    /**
     * 에러 코드.
     */
    private String errorCode = ResponseCode.EmptyResult.getValue();

    /**
     * 에러 메시지.
     */
    private String errorMessage = this.getMessage();

    /**
     * 기본 생성자.
     */
    public EmptyResultException() {
        super("No Result Data!");
    }

    /**
     * 예외 문자열을 받는 생성자.
     *
     * @param message 예외 문자열
     */
    public EmptyResultException(String message) {
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
