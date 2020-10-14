package com.skt.tsop.robot.controller.robot;

import com.skt.tsop.robot.model.TsoApiResponse;
import com.skt.tsop.robot.model.URIQueryString;
import com.skt.tsop.robot.service.RobotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 로봇 정보 REST API 컨트롤러
 * @author syjeon@ntels.com
 */
@RestController
@RequestMapping(headers = "Accept=application/json", value = "/rest")
public class RobotController {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RobotController.class);

    /**
     * RobotService
     */
    @Autowired
    private RobotService robotService;

    /**
     * 로봇 서비스 지역
     * @param param Request Body : robot_id=로봇 아이디
     * @param request 요청들어온 HttpServletRequest
     * @return TsoApiResponse
     */
    @PostMapping(value = "/location")
    public TsoApiResponse getLocation(@RequestBody Map param, HttpServletRequest request) {
        logger.debug("ROBOT REST API REQUEST: uri={}, params={}", request.getRequestURI(), param.toString());

        URIQueryString uriQueryString = new URIQueryString();
        uriQueryString.setUri(request.getRequestURI());

        TsoApiResponse tsoApiResponse = robotService.getRobotApi(uriQueryString, param);

        return tsoApiResponse;
    }

    /**
     * 로봇 이름
     * @param param Request Body : robot_id=로봇 아이디
     * @param request 요청들어온 HttpServletRequest
     * @return TsoApiResponse
     */
    @PostMapping(value = "/dispalyname")
    public TsoApiResponse getDisplayname(@RequestBody Map param, HttpServletRequest request) {
        logger.debug("ROBOT REST API REQUEST: uri={}, params={}", request.getRequestURI(), param.toString());

        URIQueryString uriQueryString = new URIQueryString();
        uriQueryString.setUri(request.getRequestURI());

        TsoApiResponse tsoApiResponse = robotService.getRobotApi(uriQueryString, param);

        return tsoApiResponse;
    }

    /**
     * 로봇 구독 리스트
     * @param param Request Body : robot_id=로봇 아이디
     * @param request 요청들어온 HttpServletRequest
     * @return TsoApiResponse
     */
    @PostMapping(value = "/topics/list")
    public TsoApiResponse getTopicList(@RequestBody Map param, HttpServletRequest request) {
        logger.debug("ROBOT REST API REQUEST: uri={}, params={}", request.getRequestURI(), param.toString());

        URIQueryString uriQueryString = new URIQueryString();
        uriQueryString.setUri(request.getRequestURI());

        TsoApiResponse tsoApiResponse = robotService.getRobotApi(uriQueryString, param);

        return tsoApiResponse;
    }

}
