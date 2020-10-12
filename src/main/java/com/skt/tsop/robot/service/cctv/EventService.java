package com.skt.tsop.robot.service.cctv;

import com.skt.tsop.robot.model.cctv.Event;
import com.skt.tsop.robot.model.URIQueryString;

/**
 * EventService.
 * @author yjkim@ntels.com
 */
public interface EventService {
    /**
     * Multi Part 이벤트 수신.
     * @param event 이벤트 정보.
     */
    void receiveMultiPartEvent(Event event, URIQueryString uriQueryString);
}
