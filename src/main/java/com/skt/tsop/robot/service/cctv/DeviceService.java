package com.skt.tsop.robot.service.cctv;

import com.skt.tsop.robot.model.cctv.Device;
import com.skt.tsop.robot.model.URIQueryString;

import java.util.Map;

/**
 * DeviceService.
 * @author yjkim@ntels.com
 */
public interface DeviceService {
    /**
     * 사용자 디바이스 채널 별 목록 조회
     * @param device 디바이스 정보.
     * @param uriQueryString HttpRequest 정보.
     * @return 디바이스 목록 정보
     */
    Map<String, Object> getDeviceList(Device device, URIQueryString uriQueryString);
}
