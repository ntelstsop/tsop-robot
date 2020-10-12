package com.skt.tsop.robot.model.cctv;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Event 모델.
 *
 * @author yjkim@ntels.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {
    /**
     * 인증 토큰.
     */
    private String auth_token;
    /**
     * API 요청 시리얼 번호.
     */
    private Integer api_serial;
    /**
     * 이벤트 정보 수신 여부.
     */
    private Boolean event;
    /**
     * 시스템 정보 수신 여부.
     */
    private Boolean system;
    /**
     * 모니터링 정보 수신 여부.
     */
    private String monitoring;
    /**
     * 유저 로그 정보 수신 여부.
     */
    private Boolean userlog;
    /**
     * 디바이스 시리얼 번호.
     */
    private Integer dev_serial;
    /**
     * 디바이스 채널.
     */
    private Integer dch_ch;
    /**
     * 이벤트 아이디.
     */
    private Integer event_id;
    /**
     * 이벤트 시간.
     */
    private String event_time;
    /**
     * 이벤트 메시지.
     */
    private String event_msg;
    /**
     * 이벤트 상태.
     */
    private Integer event_status;

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public Integer getApi_serial() {
        return api_serial;
    }

    public void setApi_serial(Integer api_serial) {
        this.api_serial = api_serial;
    }

    public Boolean getEvent() {
        return event;
    }

    public void setEvent(Boolean event) {
        this.event = event;
    }

    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    public String getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(String monitoring) {
        this.monitoring = monitoring;
    }

    public Boolean getUserlog() {
        return userlog;
    }

    public void setUserlog(Boolean userlog) {
        this.userlog = userlog;
    }

    public Integer getDev_serial() {
        return dev_serial;
    }

    public void setDev_serial(Integer dev_serial) {
        this.dev_serial = dev_serial;
    }

    public Integer getDch_ch() {
        return dch_ch;
    }

    public void setDch_ch(Integer dch_ch) {
        this.dch_ch = dch_ch;
    }

    public Integer getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_msg() {
        return event_msg;
    }

    public void setEvent_msg(String event_msg) {
        this.event_msg = event_msg;
    }

    public Integer getEvent_status() {
        return event_status;
    }

    public void setEvent_status(Integer event_status) {
        this.event_status = event_status;
    }

    @Override
    public String toString() {
        return "Event{" +
                "auth_token='" + auth_token + '\'' +
                ", api_serial=" + api_serial +
                ", event=" + event +
                ", system=" + system +
                ", monitoring='" + monitoring + '\'' +
                ", userlog=" + userlog +
                ", dev_serial=" + dev_serial +
                ", dch_ch=" + dch_ch +
                ", event_id=" + event_id +
                ", event_time='" + event_time + '\'' +
                ", event_msg='" + event_msg + '\'' +
                ", event_status=" + event_status +
                '}';
    }
}
