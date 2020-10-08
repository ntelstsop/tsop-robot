package com.skt.tsop.building.model.cctv;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Video 모델.
 * @author yjkim@ntels.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Video {
    /**
     * 인증 토큰.
     */
    private String auth_token;
    /**
     * API 요청 시리얼 번호.
     */
    private Integer api_serial;
    /**
     * 장비 시리얼 번호.
     */
    private Integer dev_serial;
    /**
     * 채널 고유 번호.
     */
    private Integer dch_ch;
    /**
     * 디바이스 채널 미디어 시리얼 번호.
     */
    private Integer dchm_serial;
    /**
     * 영상을 조회할 날짜.
     */
    private String date;
    /**
     * 영상을 조회할 날짜 시작.
     */
    private String from_date;
    /**
     * 영상을 조회할 날짜 종료.
     */
    private String to_date;
    /**
     * 시간.
     */
    private String timestamp;
    /**
     * 이미지 사이즈 너비.
     */
    private Integer width;
    /**
     * 이미지 사이즈 높이.
     */
    private Integer height;
    /**
     * 영상 재생 배속
     */
    private Integer speed;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getDchm_serial() {
        return dchm_serial;
    }

    public void setDchm_serial(Integer dchm_serial) {
        this.dchm_serial = dchm_serial;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Video{" +
                "auth_token='" + auth_token + '\'' +
                ", api_serial=" + api_serial +
                ", dev_serial=" + dev_serial +
                ", dch_ch=" + dch_ch +
                ", dchm_serial=" + dchm_serial +
                ", date='" + date + '\'' +
                ", from_date='" + from_date + '\'' +
                ", to_date='" + to_date + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", speed=" + speed +
                '}';
    }
}
