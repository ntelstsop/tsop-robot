package com.skt.tsop.robot.service;

import com.google.gson.Gson;
import com.skt.tsop.robot.exception.InvalidRequestException;
import com.skt.tsop.robot.model.TsoApiResponse;
import com.skt.tsop.robot.util.MessageBrokerUtil;
import com.skt.tsop.robot.util.RestTemplateMapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * RobotServiceImpl
 *
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
     * RestTemplateString Util
     */
    @Autowired
    private RestTemplateMapUtil restTemplateMapUtil;

    /**
     * MessageBrokerUtil
     */
    @Autowired
    private MessageBrokerUtil messageBrokerUtil;

    @Override
    public TsoApiResponse getRobotInfo(HttpServletRequest request, Map param) {
        String urlPath = request.getRequestURI();
        String url = serverUrl + urlPath;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        Map<String, Object> response = restTemplateMapUtil.restTemplate(url, HttpMethod.POST, headers, param);

        TsoApiResponse tsoApiResponse = new TsoApiResponse();
        tsoApiResponse.setUrlpath(urlPath);
        tsoApiResponse.setServicetype(serviceType);
        tsoApiResponse.setContent(response);

        return tsoApiResponse;
    }

    @Override
    public TsoApiResponse robotControl(HttpServletRequest request, Map param) {
        String robotId = request.getHeader("robot_id");

        if (robotId == null) {
            throw new InvalidRequestException("Request Header [robot_id] is required!");
        }

        String subject = request.getRequestURI().substring(1);
        String payload = new Gson().toJson(param);

        logger.debug("ROBOT_COMMAND SEND TO MESSAGE_BROKER: robot_id={}, subject={}, payload={}", robotId, subject, payload);

        TsoApiResponse tsoApiResponse = messageBrokerUtil.publish(robotId, subject, payload);

        return tsoApiResponse;
    }
}

