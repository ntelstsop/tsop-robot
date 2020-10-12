package com.skt.tsop.robot.controller.cctv;

import com.skt.tsop.robot.exception.InvalidRequestException;
import com.skt.tsop.robot.model.cctv.Event;
import com.skt.tsop.robot.model.URIQueryString;
import com.skt.tsop.robot.service.cctv.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * EventController.
 * @author yjkim@ntels.com
 */
@RestController
@RequestMapping(headers = "Accept=application/json", value = "api/event")
public class EventController {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);
    /**
     * EventHubService.
     */
    @Autowired
    EventService eventService;
    /**
     * 서비스 타입
     */
    @Value("${config.cctv.serviceType}")
    private String serviceType;

    /**
     * [CCTV-EVENT-001] 멀티파트 이벤트 수신.
     *
     * @param auth_token    인증 토큰.
     * @param api_serial    인증 API 시리얼 번호.
     * @param event         이벤트 Model.
     * @param bindingResult 유효성 체크 결과.
     * @param request       HttpServletRequest.
     */
    @GetMapping(value = "/receive")
    public void receiveMultiPartEvent(@RequestHeader(value = "x-auth-token") String auth_token,
                                        @RequestHeader(value = "x-api-serial") Integer api_serial,
                                        @Validated() Event event, BindingResult bindingResult,
                                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }
        logger.debug("MULTI_PART_EVENT_RECEIVE_STARTED");
        event.setAuth_token(auth_token);
        event.setApi_serial(api_serial + 1);
        URIQueryString uriQueryString = new URIQueryString();
        uriQueryString.setUri(request.getRequestURI());
        uriQueryString.setQueryString(request.getQueryString());

        CompletableFuture.runAsync(() -> {
            eventService.receiveMultiPartEvent(event, uriQueryString);
        });
    }
}
