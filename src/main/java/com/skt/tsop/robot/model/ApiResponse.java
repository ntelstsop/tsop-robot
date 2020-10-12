package com.skt.tsop.robot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * API 응답 공통 규격.
 *
 * @author yjkim@ntels.com
 */
@JsonInclude(Include.NON_NULL)
public class ApiResponse {

    /**
     * 결과 코드.
     */
    private String resultCode = ResponseCode.OK.getValue();
    /**
     * 결과 메시지.
     */
    private String resultMessage;
    /**
     * 결과 데이터.
     */
    private Object resultData;

    /**
     * 기본 생성자.
     */
    public ApiResponse() {
    }

    /**
     * 생성자.
     *
     * @param resultCode    결과 코드
     * @param resultMessage 결과 메시지
     */
    public ApiResponse(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

}
