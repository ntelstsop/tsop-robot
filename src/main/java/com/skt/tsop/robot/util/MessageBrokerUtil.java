package com.skt.tsop.robot.util;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import io.nats.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
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
    private ArrayList<Dispatcher> dispacherList = null;

    /**
     * NATS 서버 접속을 위한 클라이언트.
     */
    private Connection nc = null;

    @PostConstruct
    public void init() {
        try {
            Options option = this.initBrokerClient();
            nc = Nats.connect(option);
            this.initSubscribe();
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
    private void initSubscribe() {
        this.dispacherList = new ArrayList<>();

        CountDownLatch latch = new CountDownLatch(msgCount); // dispatcher runs callback in another thread

        Dispatcher d = nc.createDispatcher((msg) -> {
            System.out.printf("Received message \"%s\" on subject \"%s\"\n",
                    new String(msg.getData(), StandardCharsets.UTF_8),
                    msg.getSubject());
            latch.countDown();
        });

        //아래 방식은 싱글 스레드 이므로, 멀티로 해야 한다.
        d.subscribe("addy-id1.navigation_service.robot.pose")
                .subscribe("addy-id1.status.battery")
                .subscribe("addy-id1.navigation_service.global_planner.GlobalPlanner.plan.converted")
                .subscribe("addy-id1.lrf.scan")
                .subscribe("addy-id1.status.emergency");

        /*
        Dispatcher d = nc.createDispatcher((msg) -> {});

        Subscription s = d.subscribe("some.subject", (msg) -> {
            String response = new String(msg.getData(), StandardCharsets.UTF_8);
            System.out.println("Message received (up to 100 times): " + response);
        });
        d.unsubscribe(s, 100);
         */

        try {
            nc.flush(Duration.ZERO);
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

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
