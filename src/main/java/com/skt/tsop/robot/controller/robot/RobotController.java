package com.skt.tsop.robot.controller.robot;

import com.skt.tsop.robot.model.TsoApiResponse;
import com.skt.tsop.robot.service.RobotService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 원익로보틱스 로봇 컨트롤러
 *
 * @author syjeon@ntels.com
 */
@RestController
@RequestMapping(headers = "Accept=application/json")
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
     *
     * @param param   Request Body
     * @param request 요청들어온 HttpServletRequest
     * @return TsoApiResponse
     */
    @PostMapping(value = "/rest/robot/location")
    public TsoApiResponse getLocation(@RequestBody Map param, HttpServletRequest request) {
        logger.debug("ROBOT REST API REQUEST: uri={}, params={}", request.getRequestURI(), param.toString());

        TsoApiResponse tsoApiResponse = robotService.getRobotInfo(request, param);

        return tsoApiResponse;
    }

    /**
     * 로봇 이름
     *
     * @param param   Request Body
     * @param request 요청들어온 HttpServletRequest
     * @return TsoApiResponse
     */
    @PostMapping(value = "/rest/robot/displayname")
    public TsoApiResponse getDisplayname(@RequestBody Map param, HttpServletRequest request) {
        logger.debug("ROBOT REST API REQUEST: uri={}, params={}", request.getRequestURI(), param.toString());

        TsoApiResponse tsoApiResponse = robotService.getRobotInfo(request, param);

        return tsoApiResponse;
    }

    /**
     * 로봇 구독 리스트
     *
     * @param param   Request Body
     * @param request 요청들어온 HttpServletRequest
     * @return TsoApiResponse
     */
    @PostMapping(value = "/rest/topics/list")
    public TsoApiResponse getTopicList(@RequestBody Map param, HttpServletRequest request) {
        logger.debug("ROBOT REST API REQUEST: uri={}, params={}", request.getRequestURI(), param.toString());

        TsoApiResponse tsoApiResponse = robotService.getRobotInfo(request, param);

        return tsoApiResponse;
    }


    /**
     * 로봇 제어 - 서비스 상태 전환
     *
     * @param param   제어 Payload
     * @param request HttpServletRequest
     * @return TsoApiResponse
     * @throws JSONException
     */
    @PutMapping(value = "/cmd.context_change")
    public TsoApiResponse contextChange(@RequestBody Map param, HttpServletRequest request) {
        logger.debug("ROBOT COMMAND REQUEST: uri={}, params={}", request.getRequestURI(), param.toString());

        TsoApiResponse tsoApiResponse = robotService.robotControl(request, param);

        return tsoApiResponse;
    }

    /**
     * 로봇 제어 - 수동 로봇 이동 직진
     *
     * @param param   제어 Payload
     * @param request HttpServletRequest
     * @return TsoApiResponse
     * @throws JSONException
     */
    @PutMapping(value = "/cmd.linear_vel")
    public TsoApiResponse linearVel(@RequestBody Map param, HttpServletRequest request) {
        logger.debug("ROBOT COMMAND REQUEST: uri={}, params={}", request.getRequestURI(), param.toString());

        TsoApiResponse tsoApiResponse = robotService.robotControl(request, param);

        return tsoApiResponse;
    }

    /**
     * 로봇 제어 - 수동 로봇 이동 회전
     *
     * @param param   제어 Payload
     * @param request HttpServletRequest
     * @return TsoApiResponse
     * @throws JSONException
     */
    @PutMapping(value = "/cmd.angular_vel")
    public TsoApiResponse angularVel(@RequestBody Map param, HttpServletRequest request) {
        logger.debug("ROBOT COMMAND REQUEST: uri={}, params={}", request.getRequestURI(), param.toString());

        TsoApiResponse tsoApiResponse = robotService.robotControl(request, param);

        return tsoApiResponse;
    }

    /**
     * 로봇 제어 - 지정 위치 로봇 이동
     *
     * @param param   제어 Payload
     * @param request HttpServletRequest
     * @return TsoApiResponse
     * @throws JSONException
     */
    @PutMapping(value = "/cmd.move_to")
    public TsoApiResponse moveTo(@RequestBody Map param, HttpServletRequest request) {
        logger.debug("ROBOT COMMAND REQUEST: uri={}, params={}", request.getRequestURI(), param.toString());

        TsoApiResponse tsoApiResponse = robotService.robotControl(request, param);

        return tsoApiResponse;
    }

    /**
     * 로봇 제어 - 로봇 헤드 제어
     *
     * @param param   제어 Payload
     * @param request HttpServletRequest
     * @return TsoApiResponse
     * @throws JSONException
     */
    @PutMapping(value = "/cmd.head_control")
    public TsoApiResponse headControl(@RequestBody Map param, HttpServletRequest request) {
        logger.debug("ROBOT COMMAND REQUEST: uri={}, params={}", request.getRequestURI(), param.toString());

        TsoApiResponse tsoApiResponse = robotService.robotControl(request, param);

        return tsoApiResponse;
    }

    /**
     * 로봇 제어 - 수동 순찰 요청
     *
     * @param param   제어 Payload
     * @param request HttpServletRequest
     * @return TsoApiResponse
     * @throws JSONException
     */
    @PutMapping(value = "/cmd.manual_patrol")
    public TsoApiResponse manualPatrol(@RequestBody Map param, HttpServletRequest request) {
        logger.debug("ROBOT COMMAND REQUEST: uri={}, params={}", request.getRequestURI(), param.toString());

        TsoApiResponse tsoApiResponse = robotService.robotControl(request, param);

        return tsoApiResponse;
    }

    /**
     * 로봇 제어 - 사진 찍기
     *
     * @param param   제어 Payload
     * @param request HttpServletRequest
     * @return TsoApiResponse
     * @throws JSONException
     */
    @PutMapping(value = "/cmd.photo")
    public TsoApiResponse photo(@RequestBody Map param, HttpServletRequest request) {
        logger.debug("ROBOT COMMAND REQUEST: uri={}, params={}", request.getRequestURI(), param.toString());

        TsoApiResponse tsoApiResponse = robotService.robotControl(request, param);

        return tsoApiResponse;
    }


}
