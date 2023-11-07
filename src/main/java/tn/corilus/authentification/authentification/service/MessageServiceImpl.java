package tn.corilus.authentification.authentification.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import tn.corilus.authentification.authentification.entities.*;
import tn.corilus.authentification.authentification.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversationService conversationService;
    private final KafkaTemplate<String, Conversation> kafkaTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageServiceImpl(MessageRepository messageRepository, ConversationService conversationService, KafkaTemplate<String, Conversation> kafkaTemplate, SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.conversationService = conversationService;
        this.kafkaTemplate = kafkaTemplate;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public Message createMessage(Conversation conversation, User sender, String content) {
        Message message = new Message();
        conversation.setLastUpdatedAt(new Date());
        message.setConversation(conversation);
        message.setSender(sender);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message = messageRepository.save(message);

        // Publish the message to Kafka
        kafkaTemplate.send("chat-messages", message.getId().toString(), conversation);
        // Send the message to connected clients via web sockets
        message.setSender(sender);
        message.setConversation(conversation);
        messagingTemplate.convertAndSend("/topic/chat/" + conversation.getSupport().getId(), conversationService.getConversationBySupportIdAndArchivedIsFalse(conversation.getSupport().getId()));
        messagingTemplate.convertAndSend("/topic/doctor/" + conversation.getDoctor().getId(), conversationService.getConversationByDoctorIdAndArchivedIsFalse(conversation.getDoctor().getId()));

        return message;
    }

}
