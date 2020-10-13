package com.skt.tsop.robot.model;

/**
 * @author syjeon@ntels.com
 */
public class TsoApiResponse {

    /**
     * URI Path
     */
    private String urlpath;

    /**
     * 서비스 타입
     */
    private String servicetype;

    /**
     * 결과 데이터
     */
    private Object content;

    public String getUrlpath() {
        return urlpath;
    }

    public void setUrlpath(String urlpath) {
        this.urlpath = urlpath;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TsoApiResponse{" +
                "urlpath='" + urlpath + '\'' +
                ", servicetype='" + servicetype + '\'' +
                ", content=" + content +
                '}';
    }
}
