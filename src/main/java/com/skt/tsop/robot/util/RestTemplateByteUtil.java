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

import java.util.HashMap;
import java.util.Map;

/**
 * RestTemplateByteUtil.
 * @author yjkim@ntels.com
 */
@Component
public class RestTemplateByteUtil {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(RestTemplateByteUtil.class);
    /**
     * RestTemplate.
     */
    @Autowired
    RestTemplate restTemplate;

    public byte[] restTemplate(String url, HttpMethod httpMethod, HttpHeaders headers, Map<String, Object> bodyMap) {
        long trId = System.currentTimeMillis();
        HttpEntity<Object> entity = new HttpEntity<>(bodyMap, headers);
        logger.debug("REST_REQUEST_INFO: trId={}, url={}, method={}, body={}", trId, url, httpMethod, bodyMap);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, httpMethod, entity, byte[].class);
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
