package com.skt.tsop.robot.service;

import com.skt.tsop.robot.model.TsoApiResponse;
import org.json.JSONException;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * RobotService
 *
 * @author syjeon@ntels.com
 */
public interface RobotService {

    /**
     * 원익로보틱스 관제 서버로 Rest 요청
     *
     * @param request HttpServletRequest
     * @param param   요청 들어온 RequestBody
     * @return TsoApiResponse
     */
    TsoApiResponse getRobotInfo(HttpServletRequest request, Map param);

    /**
     * 원익로보틱스 로봇 제어
     *
     * @param request HttpServletRequest
     * @param param   요청 들어온 RequestBody: 제어 Command
     * @return TsoApiResponse
     */
    TsoApiResponse robotControl(HttpServletRequest request, Map param);
}
