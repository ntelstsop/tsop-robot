package com.skt.tsop.robot.model.cctv;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Device 모델.
 *
 * @author yjkim@ntels.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device {
    /**
     * 인증 토큰.
     */
    private String auth_token;
    /**
     * API 요청 시리얼 번호.
     */
    private Integer api_serial;
    /**
     * 사용자 시리얼 번호.
     */
    private Integer user_serial;
    /**
     * 장비 페이지 시리얼 번호.
     */
    private Integer ctx_serial;
    /**
     * 노드 시리얼.
     */
    private Integer node_serial;
    /**
     * 채널 타입.
     */
    private String channel_type;
    /**
     * 노드 이름.
     */
    private String node_name;
    /**
     * 디바이스 이름.
     */
    private String dev_name;
    /**
     * 디바이스 주소.
     */
    private String dev_addr;

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

    public Integer getUser_serial() {
        return user_serial;
    }

    public void setUser_serial(Integer user_serial) {
        this.user_serial = user_serial;
    }

    public Integer getCtx_serial() {
        return ctx_serial;
    }

    public void setCtx_serial(Integer ctx_serial) {
        this.ctx_serial = ctx_serial;
    }

    public Integer getNode_serial() {
        return node_serial;
    }

    public void setNode_serial(Integer node_serial) {
        this.node_serial = node_serial;
    }

    public String getChannel_type() {
        return channel_type;
    }

    public void setChannel_type(String channel_type) {
        this.channel_type = channel_type;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public String getDev_name() {
        return dev_name;
    }

    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }

    public String getDev_addr() {
        return dev_addr;
    }

    public void setDev_addr(String dev_addr) {
        this.dev_addr = dev_addr;
    }

    @Override
    public String toString() {
        return "Device{" +
                "auth_token='" + auth_token + '\'' +
                ", api_serial=" + api_serial +
                ", user_serial=" + user_serial +
                ", ctx_serial=" + ctx_serial +
                ", node_serial=" + node_serial +
                ", channel_type='" + channel_type + '\'' +
                ", node_name='" + node_name + '\'' +
                ", dev_name='" + dev_name + '\'' +
                ", dev_addr='" + dev_addr + '\'' +
                '}';
    }
}
