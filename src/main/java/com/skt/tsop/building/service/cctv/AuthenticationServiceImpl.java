package com.skt.tsop.building.service.cctv;

import com.skt.tsop.building.util.RestTemplateMapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * AuthenticationServiceImpl.
 * @author yjkim@ntels.com
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    /**
     * RestTemplateUtil.
     */
    @Autowired
    RestTemplateMapUtil restTemplateMapUtil;
    /**
     * AICCTV IP 주소.
     */
    @Value("${config.cctv.ip}")
    private String cctvIp;
    /**
     * AICCTV 포트번호.
     */
    @Value("${config.cctv.port}")
    private String cctvPort;
    /**
     * AICCTV 아이디.
     */
    @Value("${config.cctv.id}")
    private String cctvId;
    /**
     * AICCTV 비밀번호.
     */
    @Value("${config.cctv.password}")
    private String cctvPw;
    /**
     * AICCTV 비밀번호.
     */
    @Value("${config.cctv.group}")
    private String cctvGroup;
    /**
     * AICCTV 비밀번호.
     */
    @Value("${config.cctv.license}")
    private String cctvLicense;

    @Override
    public Map<String, Object> getAuthApiSerial() {
        String baseUrl = "http://" + cctvIp + ":" + cctvPort;
        String path = "/api/login";
        String fullUrl = baseUrl + path;
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-account-id", cctvId);
        headers.add("x-account-pass", cctvPw);
        headers.add("x-account-group", cctvGroup);
        headers.add("x-license", cctvLicense);
        return restTemplateMapUtil.restTemplate(fullUrl, HttpMethod.GET, headers, null);
    }

    @Override
    public void keepAliveAuth(String authToken) {
        String baseUrl = "http://" + cctvIp + ":" + cctvPort;
        String path = "/api/keep-alive";
        String fullUrl = baseUrl + path;
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-token", authToken);
        restTemplateMapUtil.restTemplate(fullUrl, HttpMethod.GET, headers, null);
    }

    @Override
    public Map<String, Object> removeAuthApiSerial(String authToken) {
        String baseUrl = "http://" + cctvIp + ":" + cctvPort;
        String path = "/api/logout";
        String fullUrl = baseUrl + path;
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-token", authToken);
        return restTemplateMapUtil.restTemplate(fullUrl, HttpMethod.DELETE, headers, null);
    }
}