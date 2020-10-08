package com.skt.tsop.building.exception;

import com.skt.tsop.building.model.ApiResponse;
import com.skt.tsop.building.model.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 전역적으로 예외를 처리하기 위한 핸들러.
 *
 * @author yjkim@ntels.com
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 기본 생성자.
     */
    public GlobalExceptionHandler() {
    }

    /**
     * Spring MVC에서 발생되는 예외들을 처리.
     *
     * @param req HttpServletRequest 객체
     * @param e   Exception 객체
     * @return ApiResponse
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class, MissingServletRequestParameterException.class,
            ServletRequestBindingException.class, ConversionNotSupportedException.class,
            HttpMessageNotReadableException.class, MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class, BindException.class, NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse handleRequestException(HttpServletRequest req, Exception e) {
        logger.warn("SPRING_MVC_EXCEPTION >> {} > {}", req.getRequestURI(), e.getMessage());
        return new ApiResponse(ResponseCode.InvalidReq.getValue(), e.getMessage());
    }

    /**
     * InvalidAuthorizationException 처리.
     *
     * @param req HttpServeltRequest 객체
     * @param e   Exception 객체
     * @return ApiResponse
     */
    @ExceptionHandler(InvalidAuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiResponse handleInvalidAuthorizationException(HttpServletRequest req, InvalidAuthorizationException e) {
        logger.warn("INVALID_AUTHORIZATION_EXCEPTION >> {} > {}", req.getRequestURI(), e.getMessage());
        return new ApiResponse(e.getErrorCode(), e.getErrorMessage());
    }

    /**
     * InvalidRequestException 처리.
     *
     * @param req HttpServletRequest 객체
     * @param e   Exception 객체
     * @return ApiResponse
     */
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse handleInvalidRequestException(HttpServletRequest req, InvalidRequestException e) {
        logger.warn("INVALID_REQUEST_EXCEPTION >> {} > {}", req.getRequestURI(), e.getMessage());
        return new ApiResponse(e.getErrorCode(), e.getErrorMessage());
    }

    /**
     * EmptyResultException 처리.
     *
     * @param req HttpServletRequest 객체
     * @param e   Exception 객체
     * @return ApiResponse
     */
    @ExceptionHandler(EmptyResultException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse handleEmptyResultException(HttpServletRequest req, EmptyResultException e) {
        logger.warn("EMPTY_RESULT_EXCEPTION >> {} > {}", req.getRequestURI(), e.getMessage());
        return new ApiResponse(e.getErrorCode(), e.getErrorMessage());
    }

    /**
     * ExternalApiException 처리.
     *
     * @param req HttpServletRequest 객체
     * @param e   Exception 객체
     * @return ApiResponse
     */
    @ExceptionHandler(ExternalApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponse handleExternalApiException(HttpServletRequest req, ExternalApiException e) {
        logger.error("EXTERNAL_API_EXCEPTION >> {} > {}", req.getRequestURI(), e.getMessage());
        return new ApiResponse(e.getErrorCode(), e.getErrorMessage());
    }

    /**
     * Exception 처리.
     *
     * @param req HttpServletRequest 객체
     * @param e   Exception 객체
     * @return ApiResponse
     */
    @ExceptionHandler({Exception.class, CommonException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponse handleException(HttpServletRequest req, Exception e) {
        logger.error("EXECPTION >> {} > {} > {}", req.getRequestURI(), e.getMessage(), e);
        return new ApiResponse(ResponseCode.ServerError.getValue(), e.getMessage());
    }
}
