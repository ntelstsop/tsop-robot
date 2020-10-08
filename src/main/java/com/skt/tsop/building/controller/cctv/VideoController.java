package com.skt.tsop.building.controller.cctv;

import com.skt.tsop.building.exception.InvalidRequestException;
import com.skt.tsop.building.model.URIQueryString;
import com.skt.tsop.building.model.cctv.Video;
import com.skt.tsop.building.service.cctv.AuthenticationService;
import com.skt.tsop.building.service.cctv.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * VideoController.
 * @author yjkim@ntels.com
 */
@RestController
@RequestMapping(headers = "Accept=application/json", value = "api/video")
public class VideoController {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);
    /**
     * AuthenticationService.
     */
    @Autowired
    AuthenticationService authenticationService;
    /**
     * VideoService.
     */
    @Autowired
    VideoService videoService;
    /**
     * 서비스 타입
     */
    @Value("${config.cctv.serviceType}")
    private String serviceType;

    /**
     * [CCTV-VIDEO-001] 영상 데이터 조회.
     *
     * @param dev_serial    디바이스 시리얼 번호.
     * @param dch_ch        디바이스 채널.
     * @param video         date 관련 쿼리 스트링.
     * @param bindingResult 유효성 체크 결과.
     * @param request       HttpServletRequest.
     * @return 영상 데이터 조회 결과
     */
    @GetMapping(value = "/time-range/{dev_serial}/{dch_ch}")
    public Map<String, Object> getVideoData(@PathVariable(value = "dev_serial") Integer dev_serial,
                                            @PathVariable(value = "dch_ch") Integer dch_ch,
                                            @Validated() Video video, BindingResult bindingResult,
                                            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }
        logger.debug("GET_VIDEO_DATA_STARTED");
        Map<String, Object> authMap = authenticationService.getAuthApiSerial();
        Map<String, Object> results = (Map<String, Object>) authMap.get("results");
        String authToken = (String) results.get("auth_token");
        Integer apiSerial = (Integer) results.get("api_serial");
        video.setAuth_token(authToken);
        video.setApi_serial(apiSerial + 1);

        URIQueryString uriQueryString = new URIQueryString();
        uriQueryString.setUri(request.getRequestURI());
        uriQueryString.setQueryString(request.getQueryString());

        Map<String, Object> responseMap = videoService.getVideoData(video, uriQueryString);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("urlpath", request.getRequestURI());
        resultMap.put("servicetype", serviceType);
        resultMap.put("content", responseMap);

        authenticationService.removeAuthApiSerial(authToken);
        return resultMap;
    }

    /**
     * [CCTV-VIDEO-002] 영상 썸네일 조회.
     *
     * @param dev_serial    디바이스 시리얼 번호.
     * @param dch_ch        디바이스 채널.
     * @param video         썸네일 관련 쿼리 스트링.
     * @param bindingResult 유효성 체크 결과.
     * @param request       HttpServletRequest.
     * @return 썸네일 조회 결과
     */
    @GetMapping(value = "/thumbnail/{dev_serial}/{dch_ch}")
    public byte[] getThumbnail(@PathVariable(value = "dev_serial") Integer dev_serial,
                               @PathVariable(value = "dch_ch") Integer dch_ch,
                               @Validated() Video video, BindingResult bindingResult,
                               HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }
        logger.debug("GET_VIDEO_THUMBNAIL_STARTED");
        Map<String, Object> authMap = authenticationService.getAuthApiSerial();
        Map<String, Object> results = (Map<String, Object>) authMap.get("results");
        String authToken = (String) results.get("auth_token");
        Integer apiSerial = (Integer) results.get("api_serial");
        video.setAuth_token(authToken);
        video.setApi_serial(apiSerial + 1);

        URIQueryString uriQueryString = new URIQueryString();
        uriQueryString.setUri(request.getRequestURI());
        uriQueryString.setQueryString(request.getQueryString());

        byte[] result = videoService.getThumbnail(video, uriQueryString);
        authenticationService.removeAuthApiSerial(authToken);
        return result;
    }

    /**
     * [CCTV-VIDEO-003] 영상 RTSP URL 조회.
     *
     * @param dev_serial    디바이스 시리얼 번호.
     * @param dch_ch        디바이스 채널.
     * @param dchm_serial   디바이스 미디어 채널.
     * @param video         썸네일 관련 쿼리 스트링.
     * @param bindingResult 유효성 체크 결과.
     * @param request       HttpServletRequest.
     * @return RTSP URL 조회 결과
     */
    @GetMapping(value = "/rtsp-url/{dev_serial}/{dch_ch}/{dchm_serial}")
    public Map<String, Object> getRtspUrl(@PathVariable(value = "dev_serial") Integer dev_serial,
                                          @PathVariable(value = "dch_ch") Integer dch_ch,
                                          @PathVariable(value = "dchm_serial") Integer dchm_serial,
                                          @Validated() Video video, BindingResult bindingResult,
                                          HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }
        logger.debug("GET_RTSP_URL_STARTED");
        Map<String, Object> authMap = authenticationService.getAuthApiSerial();
        Map<String, Object> results = (Map<String, Object>) authMap.get("results");
        String authToken = (String) results.get("auth_token");
        Integer apiSerial = (Integer) results.get("api_serial");
        video.setAuth_token(authToken);
        video.setApi_serial(apiSerial + 1);

        URIQueryString uriQueryString = new URIQueryString();
        uriQueryString.setUri(request.getRequestURI());
        uriQueryString.setQueryString(request.getQueryString());

        Map<String, Object> responseMap = videoService.getRtspUrl(video, uriQueryString);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("urlpath", request.getRequestURI());
        resultMap.put("servicetype", serviceType);
        resultMap.put("content", responseMap);

        authenticationService.removeAuthApiSerial(authToken);
        return resultMap;
    }
}
