package com.skt.tsop.robot.service;

import com.google.gson.Gson;
import com.skt.tsop.robot.model.ApiResponse;
import com.skt.tsop.robot.model.TsoApiResponse;
import com.skt.tsop.robot.util.MessageBrokerUtil;
import com.skt.tsop.robot.util.RestTemplateStringUtil;
import org.json.JSONException;
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

    /**
     * MessageBrokerUtil
     */
    @Autowired
    private MessageBrokerUtil messageBrokerUtil;

    @Override
    public TsoApiResponse getRobotApi(HttpServletRequest request, Map param) {
        String urlPath = request.getRequestURI();
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

    @Override
    public TsoApiResponse robotControl(HttpServletRequest request, Map param) throws JSONException {
        String robotId = request.getHeader("robot_id");
        String subject = request.getRequestURI().substring(1);
        String payload = new Gson().toJson(param);

        TsoApiResponse tsoApiResponse = messageBrokerUtil.publish(robotId,subject,payload);

        if (tsoApiResponse.getContent() == null){
            tsoApiResponse.setContent(new ApiResponse());
        }

        return tsoApiResponse;
    }
}
