package tn.corilus.authentification.authentification.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import tn.corilus.authentification.authentification.entities.Conversation;
import tn.corilus.authentification.authentification.entities.EmailDetails;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${confluent.api.key}")
    private String apiKey;

    @Value("${confluent.api.secret}")
    private String apiSecret;

    @Bean
    public ProducerFactory<String, Conversation> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put("security.protocol", "SASL_SSL");
        configProps.put("sasl.mechanism", "PLAIN");
        configProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + apiKey + "\" password=\"" + apiSecret + "\";");

        return new DefaultKafkaProducerFactory<>(configProps);
    }

 @Bean
    public ProducerFactory<String, EmailDetails> producerFactoryEmail() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put("security.protocol", "SASL_SSL");
        configProps.put("sasl.mechanism", "PLAIN");
        configProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + apiKey + "\" password=\"" + apiSecret + "\";");

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Conversation> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, EmailDetails> kafkaTemplateEmail() {
        return new KafkaTemplate<>(producerFactoryEmail());
    }

    @Bean
    public ConsumerFactory<String, Conversation> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "chat-consumer-group");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put("security.protocol", "SASL_SSL");
        configProps.put("sasl.mechanism", "PLAIN");
        configProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + apiKey + "\" password=\"" + apiSecret + "\";");

        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),new JsonDeserializer<>(Conversation.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Conversation> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Conversation> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}