package com.skt.tsop.robot.service.cctv;

import java.util.Map;

/**
 * AuthenticationService.
 * @author yjkim@ntels.com
 */
public interface AuthenticationService {
    /**
     * 토큰 및 API 시리얼 발급.
     *
     * @return 로그인 성공 정보
     */
    Map<String, Object> getAuthApiSerial();

    /**
     * 토큰 연장.
     *
     * @param authToken 연장할 토큰 정보
     */
    void keepAliveAuth(String authToken);

    /**
     * 로그 아웃 토큰 삭제).
     *
     * @param authToken 삭제할 토큰 정보.
     * @return 로그 아웃
     */
    Map<String, Object> removeAuthApiSerial(String authToken);
}
