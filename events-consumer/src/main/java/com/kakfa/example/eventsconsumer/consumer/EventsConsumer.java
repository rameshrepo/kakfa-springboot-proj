package com.kakfa.example.eventsconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventsConsumer {
    @KafkaListener(
            topics = {"library-events"}
            , autoStartup = "${libraryListener.startup:true}"
            , groupId = "library-events-listener-group")
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord);
    }
}
