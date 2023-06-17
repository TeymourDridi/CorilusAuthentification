package tn.corilus.authentification.authentification.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import tn.corilus.authentification.authentification.entities.Message;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class MessageSerializer implements Serializer<Message> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No additional configuration needed
    }

    @Override
    public byte[] serialize(String topic, Message data) {
        try {
            if (data == null)
                return null;

            // Configure ObjectMapper with JavaTimeModule for LocalDateTime support
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            data.setSender(null);
            data.setConversation(null);
            // Serialize the Message object to JSON
            String jsonString = objectMapper.writeValueAsString(data);

            // Convert the JSON string to byte array using UTF-8 encoding
            return jsonString.getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing Message", e);
        }
    }

    @Override
    public void close() {
        // No resources to close
    }
}
