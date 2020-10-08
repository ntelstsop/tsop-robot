package com.skt.tsop.building.service.cctv;

import com.skt.tsop.building.model.cctv.Event;
import com.skt.tsop.building.model.URIQueryString;

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
