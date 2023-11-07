package tn.corilus.authentification.authentification.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import tn.corilus.authentification.authentification.entities.Conversation;
import tn.corilus.authentification.authentification.entities.EmailDetails;
import tn.corilus.authentification.authentification.entities.Support;
import tn.corilus.authentification.authentification.repository.AdminRepository;
import tn.corilus.authentification.authentification.repository.ClaimRepository;
import tn.corilus.authentification.authentification.repository.ConversationRepository;
import tn.corilus.authentification.authentification.repository.DoctorRepository;
import tn.corilus.authentification.authentification.repository.MessageRepository;
import tn.corilus.authentification.authentification.repository.NotificationRepository;
import tn.corilus.authentification.authentification.repository.SupportRepository;
import tn.corilus.authentification.authentification.repository.UserRepository;
import tn.corilus.authentification.authentification.service.AdminServiceImpl;
import tn.corilus.authentification.authentification.service.ConversationService;
import tn.corilus.authentification.authentification.service.ConversationServiceImpl;
import tn.corilus.authentification.authentification.service.DoctorServiceImpl;
import tn.corilus.authentification.authentification.service.NotificationServiceImpl;
import tn.corilus.authentification.authentification.service.SupportServiceImpl;

class ConversationControllerTest {

    /**
     * Method under test: {@link ConversationController#getAllConversations(Long)}
     */
    @Test
    void testGetAllConversations() {
        // Arrange
        ConversationRepository conversationRepository = mock(ConversationRepository.class);
        ArrayList<Conversation> conversationList = new ArrayList<>();
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any()))
                .thenReturn(conversationList);
        ProducerFactory<String, EmailDetails> producerFactory = (ProducerFactory<String, EmailDetails>) mock(
                ProducerFactory.class);
        when(producerFactory.transactionCapable()).thenReturn(true);
        KafkaTemplate<String, EmailDetails> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        DoctorServiceImpl doctorService = new DoctorServiceImpl(doctorRepository,
                new AdminServiceImpl(mock(AdminRepository.class)), mock(ConversationRepository.class), kafkaTemplate);

        SupportServiceImpl supportService = new SupportServiceImpl(mock(SupportRepository.class),
                mock(ConversationRepository.class), mock(ClaimRepository.class), mock(MessageRepository.class));

        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationRepository, userRepository,
                new SimpMessagingTemplate(mock(MessageChannel.class)));

        MessageRepository messageService = mock(MessageRepository.class);

        // Act
        List<Conversation> actualAllConversations = (new ConversationController(
                new ConversationServiceImpl(conversationRepository, doctorService, supportService, notificationService,
                        messageService, new SimpMessagingTemplate(mock(MessageChannel.class))))).getAllConversations(1L);

        // Assert
        assertSame(conversationList, actualAllConversations);
        assertTrue(actualAllConversations.isEmpty());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any());
        verify(producerFactory).transactionCapable();
    }

    /**
     * Method under test: {@link ConversationController#getAllArchivedConversations(Long)}
     */
    @Test
    void testGetAllArchivedConversations() {
        // Arrange
        ConversationRepository conversationRepository = mock(ConversationRepository.class);
        ArrayList<Conversation> conversationList = new ArrayList<>();
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsTrueOrderByLastUpdatedAtDesc((Long) any()))
                .thenReturn(conversationList);
        ProducerFactory<String, EmailDetails> producerFactory = (ProducerFactory<String, EmailDetails>) mock(
                ProducerFactory.class);
        when(producerFactory.transactionCapable()).thenReturn(true);
        KafkaTemplate<String, EmailDetails> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        DoctorServiceImpl doctorService = new DoctorServiceImpl(doctorRepository,
                new AdminServiceImpl(mock(AdminRepository.class)), mock(ConversationRepository.class), kafkaTemplate);

        SupportServiceImpl supportService = new SupportServiceImpl(mock(SupportRepository.class),
                mock(ConversationRepository.class), mock(ClaimRepository.class), mock(MessageRepository.class));

        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationRepository, userRepository,
                new SimpMessagingTemplate(mock(MessageChannel.class)));

        MessageRepository messageService = mock(MessageRepository.class);

        // Act
        List<Conversation> actualAllArchivedConversations = (new ConversationController(
                new ConversationServiceImpl(conversationRepository, doctorService, supportService, notificationService,
                        messageService, new SimpMessagingTemplate(mock(MessageChannel.class))))).getAllArchivedConversations(1L);

        // Assert
        assertSame(conversationList, actualAllArchivedConversations);
        assertTrue(actualAllArchivedConversations.isEmpty());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsTrueOrderByLastUpdatedAtDesc((Long) any());
        verify(producerFactory).transactionCapable();
    }

    /**
     * Method under test: {@link ConversationController#markAllMessagesAsReaded(Long)}
     */
    @Test
    void testMarkAllMessagesAsReaded2() {
        // Arrange
        Conversation conversation = new Conversation();
        conversation.setSupport(new Support());
        Optional<Conversation> ofResult = Optional.of(conversation);
        ConversationRepository conversationRepository = mock(ConversationRepository.class);
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any()))
                .thenReturn(new ArrayList<>());
        when(conversationRepository.save(any())).thenReturn(new Conversation());
        when(conversationRepository.findById(any())).thenReturn(ofResult);
        ProducerFactory<String, EmailDetails> producerFactory = (ProducerFactory<String, EmailDetails>) mock(
                ProducerFactory.class);
        when(producerFactory.transactionCapable()).thenReturn(true);
        KafkaTemplate<String, EmailDetails> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        DoctorServiceImpl doctorService = new DoctorServiceImpl(doctorRepository,
                new AdminServiceImpl(mock(AdminRepository.class)), mock(ConversationRepository.class), kafkaTemplate);

        MessageChannel messageChannel = mock(MessageChannel.class);
        when(messageChannel.send(any())).thenReturn(true);
        SimpMessagingTemplate messagingTemplate = new SimpMessagingTemplate(messageChannel);
        SupportServiceImpl supportService = new SupportServiceImpl(mock(SupportRepository.class),
                mock(ConversationRepository.class), mock(ClaimRepository.class), mock(MessageRepository.class));

        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        // Act
        ResponseEntity<String> actualMarkAllMessagesAsReadedResult = (new ConversationController(
                new ConversationServiceImpl(conversationRepository, doctorService, supportService,
                        new NotificationServiceImpl(notificationRepository, userRepository,
                                new SimpMessagingTemplate(mock(MessageChannel.class))),
                        mock(MessageRepository.class), messagingTemplate))).markAllMessagesAsReaded(1L);

        // Assert
        assertEquals("All messages marked as readed", actualMarkAllMessagesAsReadedResult.getBody());
        assertEquals(200, actualMarkAllMessagesAsReadedResult.getStatusCodeValue());
        assertTrue(actualMarkAllMessagesAsReadedResult.getHeaders().isEmpty());
        verify(conversationRepository).save(any());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any());
        verify(conversationRepository).findById(any());
        verify(producerFactory).transactionCapable();
        verify(messageChannel).send(any());
    }

    /**
     * Method under test: {@link ConversationController#archive(Long)}
     */
    @Test
    void testArchive() {
        // Arrange
        ProducerFactory<String, EmailDetails> producerFactory = (ProducerFactory<String, EmailDetails>) mock(
                ProducerFactory.class);
        when(producerFactory.transactionCapable()).thenReturn(true);
        KafkaTemplate<String, EmailDetails> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        new DoctorServiceImpl(doctorRepository, new AdminServiceImpl(mock(AdminRepository.class)),
                mock(ConversationRepository.class), kafkaTemplate);

        ConversationService conversationService = mock(ConversationService.class);
        when(conversationService.archiveConversation(any())).thenReturn(new Conversation());

        // Act
        ResponseEntity<Conversation> actualArchiveResult = (new ConversationController(conversationService)).archive(1L);

        // Assert
        assertTrue(actualArchiveResult.hasBody());
        assertTrue(actualArchiveResult.getHeaders().isEmpty());
        assertEquals(200, actualArchiveResult.getStatusCodeValue());
        verify(producerFactory).transactionCapable();
        verify(conversationService).archiveConversation(any());
    }

    /**
     * Method under test: {@link ConversationController#getConversationByDoctorId(Long)}
     */
    @Test
    void testGetConversationByDoctorId() {
        // Arrange
        ConversationRepository conversationRepository = mock(ConversationRepository.class);
        when(conversationRepository.findConversationByArchivedIsFalseAndAndDoctorId(any()))
                .thenReturn(new ArrayList<>());
        ProducerFactory<String, EmailDetails> producerFactory = (ProducerFactory<String, EmailDetails>) mock(
                ProducerFactory.class);
        when(producerFactory.transactionCapable()).thenReturn(true);
        KafkaTemplate<String, EmailDetails> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        DoctorServiceImpl doctorService = new DoctorServiceImpl(doctorRepository,
                new AdminServiceImpl(mock(AdminRepository.class)), mock(ConversationRepository.class), kafkaTemplate);

        SupportServiceImpl supportService = new SupportServiceImpl(mock(SupportRepository.class),
                mock(ConversationRepository.class), mock(ClaimRepository.class), mock(MessageRepository.class));

        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationRepository, userRepository,
                new SimpMessagingTemplate(mock(MessageChannel.class)));

        MessageRepository messageService = mock(MessageRepository.class);

        // Act
        ResponseEntity<?> actualConversationByDoctorId = (new ConversationController(
                new ConversationServiceImpl(conversationRepository, doctorService, supportService, notificationService,
                        messageService, new SimpMessagingTemplate(mock(MessageChannel.class))))).getConversationByDoctorId(1L);

        // Assert
        assertNull(actualConversationByDoctorId.getBody());
        assertEquals(404, actualConversationByDoctorId.getStatusCodeValue());
        assertTrue(actualConversationByDoctorId.getHeaders().isEmpty());
        verify(conversationRepository).findConversationByArchivedIsFalseAndAndDoctorId((Long) any());
        verify(producerFactory).transactionCapable();
    }

    /**
     * Method under test: {@link ConversationController#getConversationByDoctorId(Long)}
     */
    @Test
    void testGetConversationByDoctorId2() {
        // Arrange
        ArrayList<Conversation> conversationList = new ArrayList<>();
        conversationList.add(new Conversation());
        ConversationRepository conversationRepository = mock(ConversationRepository.class);
        when(conversationRepository.findConversationByArchivedIsFalseAndAndDoctorId((Long) any()))
                .thenReturn(conversationList);
        ProducerFactory<String, EmailDetails> producerFactory = (ProducerFactory<String, EmailDetails>) mock(
                ProducerFactory.class);
        when(producerFactory.transactionCapable()).thenReturn(true);
        KafkaTemplate<String, EmailDetails> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        DoctorServiceImpl doctorService = new DoctorServiceImpl(doctorRepository,
                new AdminServiceImpl(mock(AdminRepository.class)), mock(ConversationRepository.class), kafkaTemplate);

        SupportServiceImpl supportService = new SupportServiceImpl(mock(SupportRepository.class),
                mock(ConversationRepository.class), mock(ClaimRepository.class), mock(MessageRepository.class));

        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationRepository, userRepository,
                new SimpMessagingTemplate(mock(MessageChannel.class)));

        MessageRepository messageService = mock(MessageRepository.class);

        // Act
        ResponseEntity<?> actualConversationByDoctorId = (new ConversationController(
                new ConversationServiceImpl(conversationRepository, doctorService, supportService, notificationService,
                        messageService, new SimpMessagingTemplate(mock(MessageChannel.class))))).getConversationByDoctorId(1L);

        // Assert
        assertTrue(actualConversationByDoctorId.hasBody());
        assertEquals(200, actualConversationByDoctorId.getStatusCodeValue());
        assertTrue(actualConversationByDoctorId.getHeaders().isEmpty());
        verify(conversationRepository).findConversationByArchivedIsFalseAndAndDoctorId(any());
        verify(producerFactory).transactionCapable();
    }

}

