package com.kakfa.example.eventsconsumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

//@Component // Example on how to do consume message with manual offset commit
@Slf4j
public class LibraryEventsConsumerManualOffset implements AcknowledgingMessageListener<Integer,String> {

    @Override
    @KafkaListener(topics = {"library-events"})
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord, Acknowledgment acknowledgment) {
        log.info("ConsumerRecord in Manual Offset Consumer: {} ", consumerRecord );
        acknowledgment.acknowledge();
    }
}
