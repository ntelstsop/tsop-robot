package com.skt.tsop.robot.controller.elevator;

import com.azure.messaging.eventhubs.EventData;
import com.google.gson.Gson;
import com.skt.tsop.robot.model.ApiResponse;
import com.skt.tsop.robot.model.ResponseCode;
import com.skt.tsop.robot.util.EventHubUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Elevator Status Noti 관련 Controller.
 *
 * @since 1.0
 * Copyright 2018 by nTesls.
 */
@RestController
@RequestMapping(headers = "Accept=application/json", value = "event/elevator/")
public class ElevatorEventController {

    /**
     * Logger.
     */
    private static Logger logger = LoggerFactory.getLogger(ElevatorEventController.class);
    /**
     * EventHubUtil.
     */
    @Autowired
    EventHubUtil eventHubUtil;

    @Value("${config.elevator.serviceType}")
    private String serviceType;

    /**
     * Elevator Status 이벤트
     *
     * @param requestParams requestParams
     * @return ApiResponse
     * @throws Exception Exception
     */
    @PostMapping(value = "status") public ApiResponse events(@RequestBody final Map requestParams) throws Exception {
        logger.info("<<<<<<<<<<<RECEIVE MESSAGE: {}", requestParams.toString());
        ApiResponse apiResponse = new ApiResponse();

        try {
            Map<String, Object> eventMap = new HashMap<>();
            eventMap.put("urlpath", "event/elevator/status");
            eventMap.put("servicetype", serviceType);
            eventMap.put("content", requestParams);

            EventData eventData = new EventData(new Gson().toJson(eventMap));
            eventHubUtil.sendDataToEventHub(eventData);

            apiResponse.setResultCode(ResponseCode.OK.getValue());
        } catch (Exception e) {
            logger.error("Event Hub send fail!!", e);
            apiResponse.setResultCode(ResponseCode.ExternalApiError.getValue());
            throw e;
        }

        return apiResponse;
    }

}
