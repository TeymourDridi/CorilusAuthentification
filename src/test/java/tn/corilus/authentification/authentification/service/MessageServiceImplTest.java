package tn.corilus.authentification.authentification.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.entities.Conversation;
import tn.corilus.authentification.authentification.entities.Doctor;
import tn.corilus.authentification.authentification.entities.Message;
import tn.corilus.authentification.authentification.entities.Support;
import tn.corilus.authentification.authentification.entities.User;
import tn.corilus.authentification.authentification.repository.MessageRepository;

@ContextConfiguration(classes = {MessageServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MessageServiceImplTest {
    @MockBean
    private ConversationService conversationService;

    @MockBean
    private KafkaTemplate<String, Conversation> kafkaTemplate;

    @MockBean
    private MessageRepository messageRepository;

    @Autowired
    private MessageServiceImpl messageServiceImpl;

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Method under test: {@link MessageServiceImpl#createMessage(Conversation, User, String)}
     */
    @Test
    void testCreateMessage4() throws MessagingException {
        // Arrange
        Message message = new Message();
        message.setId(123L);
        when(messageRepository.save((Message) any())).thenReturn(message);
        when(conversationService.getConversationByDoctorIdAndArchivedIsFalse((Long) any()))
                .thenReturn(new Conversation());
        when(conversationService.getConversationBySupportIdAndArchivedIsFalse((Long) any()))
                .thenReturn(new ArrayList<>());
        when(kafkaTemplate.send((String) any(), (String) any(), (Conversation) any()))
                .thenReturn(new CompletableFuture<>());
        doNothing().when(simpMessagingTemplate).convertAndSend((String) any(), (Object) any());

        Support support = new Support();
        support.setId(123L);

        Conversation conversation = new Conversation();
        conversation.setDoctor(new Doctor());
        conversation.setSupport(support);
        Admin admin = new Admin();

        // Act
        Message actualCreateMessageResult = messageServiceImpl.createMessage(conversation, admin,
                "Not all who wander are lost");

        // Assert
        assertSame(message, actualCreateMessageResult);
        assertSame(admin, actualCreateMessageResult.getSender());
        assertSame(conversation, actualCreateMessageResult.getConversation());
        verify(messageRepository).save((Message) any());
        verify(conversationService).getConversationBySupportIdAndArchivedIsFalse((Long) any());
        verify(conversationService).getConversationByDoctorIdAndArchivedIsFalse((Long) any());
        verify(kafkaTemplate).send((String) any(), (String) any(), (Conversation) any());
        verify(simpMessagingTemplate, atLeast(1)).convertAndSend((String) any(), (Object) any());
    }
}

