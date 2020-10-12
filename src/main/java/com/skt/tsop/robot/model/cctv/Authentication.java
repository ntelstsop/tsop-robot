package com.skt.tsop.robot.model.cctv;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Authentication 모델.
 * @author yjkim@ntels.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Authentication {
    /**
     * VURIX 로그인 아이디.
     */
    private String xAccountId;
    /**
     * VURIX 로그인 비밀번호.
     */
    private String xAccountPass;
    /**
     * VURIX 로그인 그룹.
     */
    private String xAccountGroup;
    /**
     * VURIX 로그인 라이센스.
     */
    private String xLicense;
    /**
     * 강제 로그인
     */
    private Boolean forceLogin;

    public String getxAccountId() {
        return xAccountId;
    }

    public void setxAccountId(String xAccountId) {
        this.xAccountId = xAccountId;
    }

    public String getxAccountPass() {
        return xAccountPass;
    }

    public void setxAccountPass(String xAccountPass) {
        this.xAccountPass = xAccountPass;
    }

    public String getxAccountGroup() {
        return xAccountGroup;
    }

    public void setxAccountGroup(String xAccountGroup) {
        this.xAccountGroup = xAccountGroup;
    }

    public String getxLicense() {
        return xLicense;
    }

    public void setxLicense(String xLicense) {
        this.xLicense = xLicense;
    }

    public Boolean getForceLogin() {
        return forceLogin;
    }

    public void setForceLogin(Boolean forceLogin) {
        this.forceLogin = forceLogin;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "xAccountId='" + xAccountId + '\'' +
                ", xAccountPass='" + xAccountPass + '\'' +
                ", xAccountGroup='" + xAccountGroup + '\'' +
                ", xLicense='" + xLicense + '\'' +
                ", forceLogin=" + forceLogin +
                '}';
    }
}
