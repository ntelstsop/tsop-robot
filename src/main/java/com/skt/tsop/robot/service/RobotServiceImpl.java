package com.skt.tsop.robot.service;

import com.google.gson.Gson;
import com.skt.tsop.robot.model.TsoApiResponse;
import com.skt.tsop.robot.model.URIQueryString;
import com.skt.tsop.robot.util.RestTemplateMapUtil;
import com.skt.tsop.robot.util.RestTemplateStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author syjeon@ntels.com
 */
@Service
public class RobotServiceImpl implements RobotService {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RobotServiceImpl.class);

    /**
     * Service Type
     */
    @Value("${config.robot.serviceType}")
    private String serviceType;

    /**
     * REST Server Url
     */
    @Value("${config.robot.restApiUrl}")
    private String serverUrl;

    /**
     * RestTemplateMap Util
     */
    @Autowired
    private RestTemplateStringUtil restTemplateStringUtil;

    @Override
    public TsoApiResponse getRobotApi(URIQueryString uriQueryString, Map param) {
        String urlPath = uriQueryString.getUri();
        String url = serverUrl + urlPath;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String response = restTemplateStringUtil.restTemplate(url, HttpMethod.POST, headers, param);

        TsoApiResponse tsoApiResponse = new TsoApiResponse();
        tsoApiResponse.setUrlpath(urlPath);
        tsoApiResponse.setServicetype(serviceType);
        tsoApiResponse.setContent(new Gson().fromJson(response, Map.class));

        return tsoApiResponse;
    }
}
