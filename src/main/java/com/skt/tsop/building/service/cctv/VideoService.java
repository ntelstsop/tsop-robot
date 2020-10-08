package com.skt.tsop.building.service.cctv;

import com.skt.tsop.building.model.URIQueryString;
import com.skt.tsop.building.model.cctv.Video;

import java.util.Map;

/**
 * VideoService.
 * @author yjkim@ntels.com
 */
public interface VideoService {
    /**
     * 영상 데이터 리스트 조회.
     * @param video 조회할 영상 정보.
     * @return 조회된 영상 정보
     */
    Map<String, Object> getVideoData(Video video, URIQueryString uriQueryString);

    /**
     * 현재 시간 또는 과거의 썸네일 정보 조회.
     * @param video 조회할 썸네일 정보.
     * @return 조회된 썸네일 정보
     */
    byte[] getThumbnail(Video video, URIQueryString uriQueryString);
    /**
     * RTSP URL 조회.
     * @param video 조회할 RTSP URL 정보.
     * @return 조회된 RTSP URL 정보
     */
    Map<String, Object> getRtspUrl(Video video, URIQueryString uriQueryString);
    /**
     * Image 테스트.
     */
    void testImage();
}
