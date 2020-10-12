package com.skt.tsop.robot.service.cctv;

import com.skt.tsop.robot.model.cctv.Device;
import com.skt.tsop.robot.model.URIQueryString;
import com.skt.tsop.robot.util.RestTemplateMapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * DeviceServiceImpl.
 * @author yjkim@ntels.com
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);
    /**
     * RestTemplateMapUtil.
     */
    @Autowired
    RestTemplateMapUtil restTemplateMapUtil;

    /**
     * AICCTV IP 주소.
     */
    @Value("${config.cctv.ip}")
    private String cctvIp;
    /**
     * AICCTV 포트번호.
     */
    @Value("${config.cctv.port}")
    private String cctvPort;

    @Override
    public Map<String, Object> getDeviceList(Device device, URIQueryString uriQueryString) {
        String baseUrl = "http://" + cctvIp + ":" + cctvPort;
        String path = uriQueryString.getUri() + "/" + device.getUser_serial() + "/" + device.getCtx_serial();
        if(uriQueryString.getQueryString() != null) {
            path += "?" + uriQueryString.getQueryString();
        }
        String fullUrl = baseUrl + path;
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-token", device.getAuth_token());
        headers.add("x-api-serial", device.getApi_serial().toString());
        return restTemplateMapUtil.restTemplate(fullUrl, HttpMethod.GET, headers, null);
    }
}
