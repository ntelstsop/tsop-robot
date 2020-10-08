package com.skt.tsop.building.util;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.skt.tsop.building.exception.ExternalApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * EventHubUtil.
 * @author yjkim@ntels.com
 */
@Component
public class EventHubUtil {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(EventHubUtil.class);
    /**
     * EventHubProducerClient.
     */
    private EventHubProducerClient producer;
    /**
     * EventDataBatch.
     */
    private EventDataBatch batch;
    /**
     * 이벤트 허브 ConnectionString.
     */
    @Value("${config.common.eventhub.connString}")
    private String connString;
    /**
     * 이벤트 허브 이름.
     */
    @Value("${config.common.eventhub.name}")
    private String eventHubName;

    @PostConstruct
    public void init() {
        producer = new EventHubClientBuilder()
                .connectionString(connString, eventHubName)
                .buildProducerClient();
    }

    public void sendDataToEventHub(EventData eventData) {
        logger.debug("SEND_DATA_TO_EVENT_HUB_STARTED : byte={}, string={}", eventData.getBody(), eventData.getBodyAsString());
        batch = producer.createBatch();
        logger.debug("BATCH_INFO : count={}, sizeInBytes={}, maxSize={}" ,batch.getCount(), batch.getSizeInBytes(), batch.getMaxSizeInBytes());
        try {
            batch.tryAdd(eventData);
            producer.send(batch);
            logger.debug("SEND_DATA_TO_EVENT_HUB_FINISHED");
        } catch (Exception e) {
            logger.debug("EVENT_HUB_EXCEPTION: body={}", e.getMessage());
            throw new ExternalApiException("SEND_DATA_TO_EVENT_HUB_ERROR");
        }
    }

}
