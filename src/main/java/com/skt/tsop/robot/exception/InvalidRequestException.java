package com.skt.tsop.robot.exception;

import com.skt.tsop.robot.model.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * 요청이 유효하지 않은 경우 발생되는 예외.
 *
 * @author yjkim@ntels.com
 */
public class InvalidRequestException extends CommonException {

    private static final long serialVersionUID = 8926867869893811310L;

    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(InvalidRequestException.class);

    /**
     * 에러 코드.
     */
    private String errorCode = ResponseCode.InvalidParam.getValue();

    /**
     * 에러 메시지.
     */
    private String errorMessage = this.getMessage();

    /**
     * 기본 생성자.
     */
    public InvalidRequestException() {
        super("Invalid Request!");
    }

    /**
     * 예외 문자열을 받는 생성자.
     *
     * @param message 예외 문자열
     */
    public InvalidRequestException(String message) {
        super(message);
        this.errorMessage = message;
    }

    /**
     * BindingResult를 갖고 InvalidRequestException을 발생 시킴.
     *
     * @param bindingResult 유효성 체크 결과
     */
    public InvalidRequestException(BindingResult bindingResult) {
        super("Invalid Request!");
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (FieldError error : bindingResult.getFieldErrors()) {
            logger.debug("VALIDATION_CHECK_ERROR > field:{}, violation:{}", error.getField(), error.getCode());
            sb.append("'").append(error.getField()).append("':'").append(error.getDefaultMessage()).append("'");
            if (i < bindingResult.getFieldErrors().size()) {
                sb.append(", ");
            }
            i++;
        }
        if (bindingResult.hasGlobalErrors()) {
            if (i > 1) {
                sb.append(", ");
            }
            i = 1;
            for (ObjectError error : bindingResult.getGlobalErrors()) {
                logger.debug("VALIDATION_CHECK_ERROR > global error, violation:{}", error.getCode());
                sb.append("'").append(error.getDefaultMessage()).append("'");
                if (i < bindingResult.getGlobalErrors().size()) {
                    sb.append(", ");
                }
                i++;
            }
        }
        this.errorMessage = sb.toString();
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
