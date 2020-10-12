package com.skt.tsop.robot.model;

/**
 * HttpRequestInfo 모델.
 *
 * @author yjkim@ntels.com
 */
public class URIQueryString {
    /**
     * 요청 URI.
     */
    private String uri;
    /**
     * 요청 쿼리 스트링.
     */
    private String queryString;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public String toString() {
        return "HttpRequestInfo{" +
                "uri='" + uri + '\'' +
                ", queryString='" + queryString + '\'' +
                '}';
    }
}
