package com.skt.tsop.robot.util;

import com.azure.messaging.eventhubs.EventData;
import com.google.gson.Gson;
import com.skt.tsop.robot.model.ApiResponse;
import com.skt.tsop.robot.model.ResponseCode;
import com.skt.tsop.robot.model.TsoApiResponse;
import io.nats.client.*;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * MessageBrokerUtil.
 *
 * @author kscho@ntels.com
 */
@Component
public class MessageBrokerUtil {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(MessageBrokerUtil.class);

    /**
     * 브로커 IP.
     */
    @Value("${config.robot.brokerip}")
    private String brokerip;

    /**
     * 브로커 PORT.
     */
    @Value("${config.robot.brokerport}")
    private String brokerport;

    /**
     * 시연을 위한 Robot ID.
     */
    @Value("${config.robot.tempRobotID}")
    private String tempRobotID;

    private int msgCount = 100;

    /**
     * NATS 클라이언트의 Subscribe을 위한 Sub 항목 리스트.
     */
    private ArrayList<String> subscriptionList = null;

    /**
     * NATS 서버 접속을 위한 클라이언트.
     */
    private Connection nc = null;

    /**
     * EventHub로 데이터를 전송하기 위한 객체.
     */
    @Autowired
    EventHubUtil eventHubUtil;

    /**
     * Subscription을 걸기 위한 Dispatcher 객체
     */
    private Dispatcher dispatcher = null;

    /**
     * Subscription List.
     */
    private String[] sublist = {
            ".status.battery",
            ".navigation_service.robot.pose",
            ".navigation_service.global_planner.GlobalPlanner.plan.converted",
            ".lrf.scan",
            ".status.emergency",
            ".service.status",
            ".reception.event.mask_check",
            ".auto_patrol.event.people_detect",
            ".service.auto_patrol.photo_result",
            ".cmd.move_to.result",
            ".cmd.manual_patrol.result",
            ".cmd.photo.result"
    };

    /**
     * Robot Service Type.
     */
    @Value("${config.robot.serviceType}")
    private String serviceType;

    @PostConstruct
    public void init() {
        try {
            Options option = this.initBrokerClient();
            nc = Nats.connect(option);

            this.initSubscribe(tempRobotID);
        } catch (IOException | InterruptedException e) {
            logger.error("init error : " + e.getCause());
            e.printStackTrace();
        }
    }

    /**
     * NATS 클라이언트에 여러개의 SUB을 거는 메소드
     */
    private void initSubscribe(String robotID) {
        if (robotID == null)
            return;

        try {
            dispatcher = nc.createDispatcher((msg) -> {
            });

            subscriptionList = new ArrayList<>();

            for (String subscription : sublist) {
                Subscription sub = dispatcher.subscribe(robotID + subscription, (msg) -> {
                    String receiveData = new String(msg.getData(), StandardCharsets.UTF_8);

                    //Json 문자열 "\n" "공백" 제거
                    Gson gson = new Gson();
                    Map<String, Object> receiveDataMap = gson.fromJson(receiveData, Map.class);
                    String convertData = gson.toJson(receiveDataMap);
                    logger.info(robotID + subscription + " Message received : " + convertData);

                    TsoApiResponse response = new TsoApiResponse();
                    response.setUrlpath(subscription.replaceFirst(".", ""));
                    response.setServicetype(serviceType);
                    response.setContent(receiveDataMap);
                    EventData eventData = new EventData(gson.toJson(response));
                    eventHubUtil.sendDataToEventHub(eventData);
                });
                subscriptionList.add(sub.getSubject());
            }
            nc.flush(Duration.ZERO);
        } catch (InterruptedException | TimeoutException e) {
            logger.error("initSubscribe Error : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * NATS 클라이언트에 여러개의 UnSubscription을 거는 메소드
     */
    private void initUnSubscribe() {
        if (subscriptionList != null && subscriptionList.size() > 0) {
            for (String sub : subscriptionList)
                dispatcher.unsubscribe(sub);
        }
    }

    /**
     * NATS 서버에 제어를 전송하기 위한 메소드
     * */
    /**
     * idle connection monitor.
     *
     * @param robotid robotid
     * @param subject subject
     * @param payload payload
     * @return TsoApiResponse
     */
    public TsoApiResponse publish(String robotid, String subject, String payload) {
        TsoApiResponse response = new TsoApiResponse();
        Message message = null;

        response.setUrlpath(subject);
        response.setServicetype(this.serviceType);

        logger.info("subject {}, payload {}", robotid + "." + subject, payload);

        try {
            nc.publish(robotid + "." + subject, payload.getBytes(StandardCharsets.UTF_8));
            response.setContent(new ApiResponse());
        } catch (Exception e) {
            logger.error("NATS InterruptedException : " + e);

            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put("error", ResponseCode.ServerError.getValue());
            response.setContent(errorMessage);
        }

        return response;
    }

    /**
     * nats 서버에 접속하기 위한 nats 클라이언트 옵션 설정 메소드
     *
     * @return Builder
     */
    private Options initBrokerClient() {
        Options.Builder builder = new Options.Builder().
                server("nats://" + this.brokerip + ":" + this.brokerport).
                connectionTimeout(Duration.ofSeconds(5)).
                pingInterval(Duration.ofSeconds(10)).
                reconnectWait(Duration.ofSeconds(1)).
                maxReconnects(-1).
                traceConnection();

        builder = builder.connectionListener((conn, type) -> System.out.println("Status change " + type));

        builder = builder.errorListener(new ErrorListener() {
            @Override
            public void slowConsumerDetected(Connection conn, Consumer consumer) {
                logger.error("NATS connection slow consumer detected");
            }

            @Override
            public void exceptionOccurred(Connection conn, Exception exp) {
                logger.error("NATS connection exception occurred");
                exp.printStackTrace();
            }

            @Override
            public void errorOccurred(Connection conn, String error) {
                logger.error("NATS connection error occurred " + error);
            }
        });

        return builder.build();
    }
}
