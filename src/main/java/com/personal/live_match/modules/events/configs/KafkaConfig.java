package com.personal.live_match.modules.events.configs;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.personal.live_match.modules.events.entities.MatchEventMessage;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @NonNull
    private Map<String, Object> consumerConfig(String groupId) {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, MatchEventMessage.class);

        return config;
    }

    @Bean
    public NewTopic matchEvents() {
        return TopicBuilder.name("match_events")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    @NonNull
    public ConsumerFactory<String, MatchEventMessage> databaseConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig("database"));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MatchEventMessage> databaseContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MatchEventMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(databaseConsumerFactory());
        factory.setConcurrency(3);

        return factory;
    }

    @Bean
    @NonNull
    public ConsumerFactory<String, MatchEventMessage> cacheConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig("cache"));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MatchEventMessage> cacheContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MatchEventMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cacheConsumerFactory());
        factory.setConcurrency(3);

        return factory;
    }
}
