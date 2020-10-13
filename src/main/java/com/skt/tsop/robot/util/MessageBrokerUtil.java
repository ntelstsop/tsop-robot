package com.skt.tsop.robot.util;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.google.gson.Gson;
import io.nats.client.*;
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
 * @author kscho@ntels.com
 */
@Component
public class MessageBrokerUtil {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(EventHubUtil.class);

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

    private int msgCount = 10;

    /**
     * NATS 클라이언트의 Subscribe을 위한 Sub 항목 리스트.
     */
    private ArrayList<Dispatcher> dispatchersArrayList = null;

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
     * Subscription List.
     */
    private String[] sublist = {".navigation_service.robot.pose", ".status.battery"
            , ".navigation_service.global_planner.GlobalPlanner.plan.converted"
            , ".lrf.scan", ".status.emergency", ".service.status"};

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
            this.initSubscribe("addy-id1");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void request() {
        Future<Message> incoming = nc.request("subject", "hello world".getBytes(StandardCharsets.UTF_8));
        Message msg = null;
        try {
            msg = incoming.get(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        String response = new String(msg.getData(), StandardCharsets.UTF_8);
    }

    /**
     * NATS 클라이언트에 여러개의 SUB을 거는 메소드
     * */
    private void initSubscribe(String robotID) {

        if(robotID == null)
            return;

        this.dispatchersArrayList = new ArrayList<>();

        CountDownLatch latch = new CountDownLatch(msgCount); // dispatcher runs callback in another thread

        try {
            for(String subscription : sublist) {
                Dispatcher despatcher = nc.createDispatcher((msg) -> {});

                Subscription sub = despatcher.subscribe(robotID + subscription, (msg) -> {
                    String response = new String(msg.getData(), StandardCharsets.UTF_8);
                    logger.info(robotID + subscription + " Message received : " + response);

                    /*
                    if(subscription.equals(".navigation_service.robot.pose")) {

                    } else if(subscription.equals(".status.battery")) {

                    } else if(subscription.equals(".navigation_service.global_planner.GlobalPlanner.plan.converted")) {

                    } else if(subscription.equals(".lrf.scan")) {

                    } else if(subscription.equals(".status.emergency")) {

                    } else if(subscription.equals(".service.status")) {

                    }
                    */

                    Map<String, Object> eventMap = new HashMap<>();
                    eventMap.put("urlpath", subscription.replaceFirst(".", ""));
                    eventMap.put("servicetype", serviceType);
                    eventMap.put("content", response);
                    EventData eventData = new EventData(new Gson().toJson(eventMap));
                    eventHubUtil.sendDataToEventHub(eventData);
                });

                this.dispatchersArrayList.add(despatcher);
            }

            nc.flush(Duration.ZERO);
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * NATS 클라이언트에 여러개의 SUB을 거는 메소드
     * */
    /*
    private void initUnSubscribe() {
        if(dispatchersArrayList != null && dispatchersArrayList.size() > 0) {
            for(Dispatcher dispatcher : dispatchersArrayList) {
               dispatcher.get
            }

            nc.flush(Duration.ZERO);
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * nats 서버에 접속하기 위한 nats 클라이언트 옵션 설정 메소드
     * @return Builder
     * */
    private Options initBrokerClient() {
        Options.Builder builder = new Options.Builder().
                server("nats://" + this.brokerip + ":" + this.brokerport).
                connectionTimeout(Duration.ofSeconds(5)).
                pingInterval(Duration.ofSeconds(10)).
                reconnectWait(Duration.ofSeconds(1)).
                maxReconnects(-1).
                traceConnection();

        builder = builder.connectionListener((conn, type) -> System.out.println("Status change "+type));

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
