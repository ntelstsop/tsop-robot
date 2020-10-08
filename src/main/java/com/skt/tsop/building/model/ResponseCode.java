package com.skt.tsop.building.model;

/**
 * 응답 코드.
 *
 * @author yjkim@ntels.com
 */
public enum ResponseCode {
    /**
     * 성공.
     */
    OK("OK"),
    /**
     * 요청이 유효하지 않음.
     */
    InvalidReq("INVALID_REQUEST"),
    /**
     * 요청 파라미터가 유효하지 않음.
     */
    InvalidParam("INVALID_PARAMETER"),
    /**
     * 접근 권한이 없음.
     */
    AccessDenied("ACCESS_DENIED"),
    /**
     * 내부 오류 발생함.
     */
    ServerError("INTERNAL_ERROR"),
    /**
     * 해당하는 데이터가 없음.
     */
    EmptyResult("EMPTY_RESULT"),
    /**
     * 외부 API 통신 오류.
     */
    ExternalApiError("EXTERNAL_API_ERROR");


    /**
     * 값.
     */
    private final String value;

    /**
     * 응답 코드.
     *
     * @param value 값
     */
    ResponseCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }}
