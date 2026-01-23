package com.kirana.finalphase1.config;

import com.kirana.finalphase1.dto.ReportRequestMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Kafka consumer config.
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    /**
     * Consumer factory consumer factory.
     *
     * @return the consumer factory
     */
    @Bean
    public ConsumerFactory<String, ReportRequestMessage> consumerFactory() {

        JsonDeserializer<ReportRequestMessage> deserializer =
                new JsonDeserializer<>(ReportRequestMessage.class);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "report-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    /**
     * Kafka listener container factory concurrent kafka listener container factory.
     *
     * @return the concurrent kafka listener container factory
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReportRequestMessage>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, ReportRequestMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
