package com.skt.tsop.building.service.cctv;

import com.azure.messaging.eventhubs.EventData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.skt.tsop.building.exception.ExternalApiException;
import com.skt.tsop.building.model.cctv.Event;
import com.skt.tsop.building.model.URIQueryString;
import com.skt.tsop.building.util.EventHubUtil;
import com.skt.tsop.building.util.RestTemplateMapUtil;
import com.skt.tsop.building.util.RestTemplateStringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * EventServiceImpl.
 * @author yjkim@ntels.com
 */
@Service
public class EventServiceImpl implements EventService {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
    /**
     * RestTemplateMapUtil.
     */
    @Autowired
    RestTemplateMapUtil restTemplateMapUtil;
    /**
     * RestTemplateMapUtil.
     */
    @Autowired
    RestTemplateStringUtil restTemplateStringUtil;
    /**
     * EventHubUtil.
     */
    @Autowired
    EventHubUtil eventHubUtil;
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
    public void receiveMultiPartEvent(Event event, URIQueryString uriQueryString) {
        String baseUrl = "http://" + cctvIp + ":" + cctvPort;
        String path = uriQueryString.getUri();
        if(uriQueryString.getQueryString() != null) {
            path += "?" + uriQueryString.getQueryString();
        }
        String fullUrl = baseUrl + path;
        CloseableHttpClient httpClient = getHttpClient(0);
        HttpGet request = new HttpGet(fullUrl);
        request.setHeader("x-auth-token", event.getAuth_token());
        request.setHeader("x-api-serial", event.getApi_serial().toString());

        try {
            while (true) {
                logger.debug("REST_REQUEST_INFO: url={}, method={}, body={}", fullUrl, "GET", null);
                HttpResponse response = httpClient.execute(request);
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");
                String jsonString = responseString.substring(responseString.indexOf("{"), responseString.lastIndexOf("}") + 1);
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
                if(jsonObject.get("code").equals("200")) {
                    logger.debug("RESULT: " + jsonObject);
                    eventHubUtil.sendDataToEventHub(new EventData(jsonString));
                } else {
                    logger.debug("ERROR: " + jsonObject);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExternalApiException();
        }
    }

    public CloseableHttpClient getHttpClient(int timeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout).build();
        HttpClientBuilder httpClientBuilder = HttpClients.custom().setDefaultRequestConfig(requestConfig);
        return httpClientBuilder.build();
    }
}
