package com.skt.tsop.robot.exception;

import com.skt.tsop.robot.model.ResponseCode;

/**
 * 외부 API 통신시 발생한 예외.
 *
 * @author yjkim@ntels.com
 */
public class ExternalApiException extends CommonException {

    private static final long serialVersionUID = 9096390499082704598L;

    /**
     * 에러 코드.
     */
    private String errorCode = ResponseCode.ExternalApiError.getValue();

    /**
     * 에러 메시지.
     */
    private String errorMessage = this.getMessage();

    /**
     * 기본 생성자.
     */
    public ExternalApiException() {
        super("External API Error!");
    }

    /**
     * 예외 문자열을 받는 생성자.
     *
     * @param message 예외 문자열
     */
    public ExternalApiException(String message) {
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
