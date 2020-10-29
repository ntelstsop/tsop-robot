package com.skt.tsop.robot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * RestTemplate 활용 Http Client 객체
 *
 * @author syjeon@ntels.com
 */
@Component
public class RestTemplateUtil {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RestTemplateUtil.class);

    /**
     * RestTemplate
     */
    @Autowired
    private RestTemplate restTemplate;


    /**
     * Http Client 수행
     * @param url           요청 URL
     * @param httpMethod    요청 Method
     * @param headers       요청 Headers
     * @param bodyMap       요청 Body
     * @param type          반환 클래스
     * @param <T>           반환 클래스 타입
     * @return T Object
     */
    public <T> T restTemplate(String url, HttpMethod httpMethod, HttpHeaders headers, Map<String, Object> bodyMap, Class<T> type) {
        long trId = System.currentTimeMillis();
        logger.debug("REST_REQUEST_INFO: trId={}, url={}, method={}, body={}", trId, url, httpMethod, bodyMap);
        HttpEntity<Object> httpEntity = new HttpEntity<>(bodyMap, headers);

        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, type);
            logger.debug("REST_RESPONSE_INFO: trId={}, statusCode={}, body={}", trId, responseEntity.getStatusCode(), responseEntity.getBody());
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            logger.debug("REST_RESPONSE_ERROR_INFO: trId={}, statusCode={}, body={}", trId, e.getStatusCode(), e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            logger.debug("REST_EXCEPTION: trId={}, body={}", trId, e.getMessage());
            throw e;
        }
    }


}

