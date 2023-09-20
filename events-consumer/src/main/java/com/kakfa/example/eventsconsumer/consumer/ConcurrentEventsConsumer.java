package com.kakfa.example.eventsconsumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.ContainerCustomizer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.Objects;

@Component
@Slf4j
public class ConcurrentEventsConsumer {

    private final KafkaProperties properties;

    public ConcurrentEventsConsumer(KafkaProperties properties) {
        this.properties = properties;
    }

    //@Bean
    // Use this bean if needed to configure the ListenerContainer for (Concurrency, Acknowledgment mode etc.,)
    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
                                                                                ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory,
                                                                                ObjectProvider<ContainerCustomizer<Object, Object,
                                                                                        ConcurrentMessageListenerContainer<Object, Object>>> kafkaContainerCustomizer) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory();
        configurer.configure(factory, (ConsumerFactory)kafkaConsumerFactory.getIfAvailable(() -> {
            return new DefaultKafkaConsumerFactory(this.properties.buildConsumerProperties());
        }));
        Objects.requireNonNull(factory);
        kafkaContainerCustomizer.ifAvailable(factory::setContainerCustomizer);

        // Configure number of concurrent consumers
        factory.setConcurrency(3);

        // Set Acknowledgment mode to Manual
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        return factory;
    }
}
