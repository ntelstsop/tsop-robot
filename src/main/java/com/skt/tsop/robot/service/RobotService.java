package com.skt.tsop.robot.service;

import com.skt.tsop.robot.model.TsoApiResponse;
import org.json.JSONException;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author syjeon@ntels.com
 */
public interface RobotService {

    /**
     * 원익로보틱스 관제 서버로 RestApi 요청
     * @param request HttpServletRequest
     * @param param 요청 들어온 RequestBody
     * @return TsoApiResponse
     */
    TsoApiResponse getRobotApi(HttpServletRequest request, Map param);

    /**
     * 원익로보틱스 로봇 제어
     * @param request HttpServletRequest
     * @param param 요청 들어온 RequestBody: 제어 Command
     * @return TsoApiResponse
     * @throws JSONException
     */
    TsoApiResponse robotControl(HttpServletRequest request, Map param) throws JSONException;
}
