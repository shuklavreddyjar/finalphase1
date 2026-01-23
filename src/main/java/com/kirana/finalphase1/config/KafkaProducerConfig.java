package com.kirana.finalphase1.config;

import com.kirana.finalphase1.dto.ReportRequestMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Kafka producer config.
 */
@Configuration
public class KafkaProducerConfig {

    /**
     * Producer factory producer factory.
     *
     * @return the producer factory
     */
    @Bean
    public ProducerFactory<String, ReportRequestMessage> producerFactory() {

        Map<String, Object> configProps = new HashMap<>();

        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092"
        );

        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class
        );

        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class
        );

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Kafka template kafka template.
     *
     * @return the kafka template
     */
    @Bean
    public KafkaTemplate<String, ReportRequestMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
