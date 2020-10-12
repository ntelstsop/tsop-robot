package com.skt.tsop.robot.controller.cctv;

import com.azure.messaging.eventhubs.EventData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.skt.tsop.robot.service.cctv.AuthenticationService;
import com.skt.tsop.robot.util.EventHubUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class MultiPartEventController {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(MultiPartEventController.class);
    /**
     * AuthenticationService.
     */
    @Autowired
    AuthenticationService authenticationService;
    /**
     * EventHubUtil.
     */
    @Autowired
    EventHubUtil eventHubUtil;
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
     * EVENT 속성 수신 여부.
     */
    @Value("${config.cctv.receiveEvent.event}")
    private String event;
    /**
     * SYSTEM 속성 수신 여부.
     */
    @Value("${config.cctv.receiveEvent.system}")
    private String system;
    /**
     * MONITORING 속성 수신 여부.
     */
    @Value("${config.cctv.receiveEvent.monitoring}")
    private String monitoring;
    /**
     * USERLOG 속성 수신 여부.
     */
    @Value("${config.cctv.receiveEvent.userlog}")
    private String userlog;
    /**
     * 초기 토큰.
     */
    private String initAuthToken;

    @PostConstruct
    public void init() {
        Map<String, Object> authMap = getAuth();
        Map<String, Object> results = (Map<String, Object>) authMap.get("results");
        initAuthToken = (String) results.get("auth_token");
        Integer apiSerial = (Integer) results.get("api_serial");
        receiveEvent(initAuthToken, apiSerial + 1);

        // for test
        //receiveEventTest();
    }

    /**
     * 로그인.
     * @return 로그인 결과.
     */
    public Map<String, Object>  getAuth() {
        Map<String, Object> authMap = authenticationService.getAuthApiSerial();
        return authMap;
    }

    /**
     * KEEP ALIVE 토큰 연장 (5분 간격).
     */
    @Scheduled(fixedDelay = 300000)
    public void keepAliveMessage() {
        logger.debug("KEEP_ALIVE_TOKEN : " + initAuthToken);
        authenticationService.keepAliveAuth(initAuthToken);
    }

    /**
     * 이벤트 수신.
     * @param authToken 인증 토큰.
     * @param apiSerial 인증 시리얼.
     */
    public void receiveEvent(String authToken, Integer apiSerial) {
        String baseUrl = "http://" + cctvIp + ":" + cctvPort + "/api/event/receive";
        String queryString = "?event=" + event + "&system=" + system + "&monitoring=" + monitoring + "&userlog=" + userlog;
        String fullUrl = baseUrl + queryString;

        CloseableHttpClient httpClient = getHttpClient(0);
        HttpGet request = new HttpGet(fullUrl);
        request.setHeader("x-auth-token", authToken);
        request.setHeader("x-api-serial", apiSerial.toString());
        logger.debug("REST_REQUEST_INFO: url={}, method={}, body={}", fullUrl, "GET", null);
        CompletableFuture.runAsync(() -> {
            while (true) {
                try {
                    HttpResponse response = httpClient.execute(request);
                    HttpEntity entity = response.getEntity();
                    String responseString = EntityUtils.toString(entity, "UTF-8");
                    String jsonString = responseString.substring(responseString.indexOf("{"), responseString.lastIndexOf("}") + 1);
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
                    if(jsonObject.get("code").equals("200")) {
                        logger.debug("RESULT: " + jsonObject);
                        eventHubUtil.sendDataToEventHub(new EventData(jsonString));
                    } else if(jsonObject.get("code").equals("403")) {
                        Map<String, Object> authMap = getAuth();
                        Map<String, Object> results = (Map<String, Object>) authMap.get("results");
                        initAuthToken = (String) results.get("auth_token");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 이벤트 수신용 HttpClient 타임아웃 설정.
     * @param timeout 타임아웃.
     * @return HttpClient
     */
    public CloseableHttpClient getHttpClient(int timeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout).build();
        HttpClientBuilder httpClientBuilder = HttpClients.custom().setDefaultRequestConfig(requestConfig);
        return httpClientBuilder.build();
    }

    public void receiveEventTest() {
        String fullUrl = "http://localhost:8001/test";
        CloseableHttpClient httpClient = getHttpClient(0);
        HttpGet request = new HttpGet(fullUrl);
        logger.debug("REST_REQUEST_INFO: url={}, method={}, body={}", fullUrl, "GET", null);
        CompletableFuture.runAsync(() -> {
            while (true) {
                try {
                    HttpResponse response = httpClient.execute(request);
                    HttpEntity entity = response.getEntity();
                    String responseString = EntityUtils.toString(entity, "UTF-8");
                    logger.debug("RESULT: " + responseString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
