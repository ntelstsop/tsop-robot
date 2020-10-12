package com.skt.tsop.robot.controller.cctv;

import com.skt.tsop.robot.exception.InvalidRequestException;
import com.skt.tsop.robot.model.cctv.Device;
import com.skt.tsop.robot.model.URIQueryString;
import com.skt.tsop.robot.service.cctv.AuthenticationService;
import com.skt.tsop.robot.service.cctv.DeviceService;
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
 * DeviceContrller.
 * @author yjkim@ntels.com
 */
@RestController
@RequestMapping(headers = "Accept=application/json", value = "api/device")
public class DeviceContrller {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(DeviceContrller.class);
    /**
     * AuthenticationService.
     */
    @Autowired
    AuthenticationService authenticationService;
    /**
     * DeviceService
     */
    @Autowired
    DeviceService deviceService;
    /**
     * 서비스 타입
     */
    @Value("${config.cctv.serviceType}")
    private String serviceType;
    /**
     * 서비스 타입
     */
    @Value("${config.cctv.device.ctxSerial}")
    private Integer ctxSerial;

    @GetMapping(value = "/detail-list")
    public Map<String, Object> getDeviceList(@Validated() Device device, BindingResult bindingResult,
                                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }
        logger.debug("GET_DEVICE_LIST_STARTED");
        Map<String, Object> authMap = authenticationService.getAuthApiSerial();
        Map<String, Object> results = (Map<String, Object>) authMap.get("results");
        String authToken = (String) results.get("auth_token");
        Integer apiSerial = (Integer) results.get("api_serial");
        Integer userSerial = (Integer) results.get("user_serial");
        device.setAuth_token(authToken);
        device.setApi_serial(apiSerial + 1);
        device.setUser_serial(userSerial);
        device.setCtx_serial(ctxSerial);

        URIQueryString uriQueryString = new URIQueryString();
        uriQueryString.setUri(request.getRequestURI());
        uriQueryString.setQueryString(request.getQueryString());
        Map<String, Object> responseMap = deviceService.getDeviceList(device, uriQueryString);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("urlpath", request.getRequestURI());
        resultMap.put("servicetype", serviceType);
        resultMap.put("content", responseMap);

        authenticationService.removeAuthApiSerial(authToken);
        return resultMap;
    }
}
