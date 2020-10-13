package com.skt.tsop.robot.service;

import com.skt.tsop.robot.model.TsoApiResponse;
import com.skt.tsop.robot.model.URIQueryString;


import java.util.Map;

/**
 * @author syjeon@ntels.com
 */
public interface RobotService {

    /**
     * 원익로보틱스 관제 서버로 RestApi 요청
     * @param uriQueryString urlPath값을 포함하는 객체
     * @param param 요청 들어온 RequestBody
     * @return TsoApiResponse
     */
    TsoApiResponse getRobotApi(URIQueryString uriQueryString, Map param);
}
