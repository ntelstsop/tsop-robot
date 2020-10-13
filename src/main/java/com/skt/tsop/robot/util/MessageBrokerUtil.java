package com.skt.tsop.robot.util;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Nats;
import io.nats.client.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

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

    private int msgCount = 100;

    @PostConstruct
    public void init() {
        try {
            Connection nc = Nats.connect("nats://15.164.145.178:4222");

            Subscription sub = nc.subscribe("addy-id1.navigation_service.robot.pose");
            nc.flush(Duration.ofSeconds(5));

            for(int i=0;i<msgCount;i++) {
                Message msg = sub.nextMessage(Duration.ofHours(1));

                System.out.printf("Received message \"%s\" on subject \"%s\"\n",
                        new String(msg.getData(), StandardCharsets.UTF_8),
                        msg.getSubject());
            }

            nc.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
