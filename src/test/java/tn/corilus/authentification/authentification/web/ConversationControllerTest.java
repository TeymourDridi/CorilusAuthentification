package tn.corilus.authentification.authentification.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import tn.corilus.authentification.authentification.entities.Conversation;
import tn.corilus.authentification.authentification.entities.Support;
import tn.corilus.authentification.authentification.repository.AdminRepository;
import tn.corilus.authentification.authentification.repository.ConversationRepository;
import tn.corilus.authentification.authentification.repository.DoctorRepository;
import tn.corilus.authentification.authentification.repository.MessageRepository;
import tn.corilus.authentification.authentification.repository.NotificationRepository;
import tn.corilus.authentification.authentification.repository.SupportRepository;
import tn.corilus.authentification.authentification.repository.UserRepository;
import tn.corilus.authentification.authentification.service.AdminServiceImpl;
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
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        DoctorServiceImpl doctorService = new DoctorServiceImpl(doctorRepository,
                new AdminServiceImpl(mock(AdminRepository.class)));

        SupportServiceImpl supportService = new SupportServiceImpl(mock(SupportRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationRepository, userRepository,
                new SimpMessagingTemplate(mock(MessageChannel.class)));

        MessageRepository messageService = mock(MessageRepository.class);

        // Act
        List<Conversation> actualAllConversations = (new ConversationController(
                new ConversationServiceImpl(conversationRepository, doctorService, supportService, notificationService,
                        messageService, new SimpMessagingTemplate(mock(MessageChannel.class))))).getAllConversations(123L);

        // Assert
        assertSame(conversationList, actualAllConversations);
        assertTrue(actualAllConversations.isEmpty());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any());
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
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        DoctorServiceImpl doctorService = new DoctorServiceImpl(doctorRepository,
                new AdminServiceImpl(mock(AdminRepository.class)));

        SupportServiceImpl supportService = new SupportServiceImpl(mock(SupportRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationRepository, userRepository,
                new SimpMessagingTemplate(mock(MessageChannel.class)));

        MessageRepository messageService = mock(MessageRepository.class);

        // Act
        List<Conversation> actualAllArchivedConversations = (new ConversationController(
                new ConversationServiceImpl(conversationRepository, doctorService, supportService, notificationService,
                        messageService, new SimpMessagingTemplate(mock(MessageChannel.class))))).getAllArchivedConversations(123L);

        // Assert
        assertSame(conversationList, actualAllArchivedConversations);
        assertTrue(actualAllArchivedConversations.isEmpty());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsTrueOrderByLastUpdatedAtDesc((Long) any());
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
        when(conversationRepository.save((Conversation) any())).thenReturn(new Conversation());
        when(conversationRepository.findById((Long) any())).thenReturn(ofResult);
        MessageChannel messageChannel = mock(MessageChannel.class);
        when(messageChannel.send((Message<Object>) any())).thenReturn(true);
        SimpMessagingTemplate messagingTemplate = new SimpMessagingTemplate(messageChannel);
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        DoctorServiceImpl doctorService = new DoctorServiceImpl(doctorRepository,
                new AdminServiceImpl(mock(AdminRepository.class)));

        SupportServiceImpl supportService = new SupportServiceImpl(mock(SupportRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        // Act
        ResponseEntity<String> actualMarkAllMessagesAsReadedResult = (new ConversationController(
                new ConversationServiceImpl(conversationRepository, doctorService, supportService,
                        new NotificationServiceImpl(notificationRepository, userRepository,
                                new SimpMessagingTemplate(mock(MessageChannel.class))),
                        mock(MessageRepository.class), messagingTemplate))).markAllMessagesAsReaded(123L);

        // Assert
        assertEquals("All messages marked as readed", actualMarkAllMessagesAsReadedResult.getBody());
        assertEquals(200, actualMarkAllMessagesAsReadedResult.getStatusCodeValue());
        assertTrue(actualMarkAllMessagesAsReadedResult.getHeaders().isEmpty());
        verify(conversationRepository).save((Conversation) any());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any());
        verify(conversationRepository).findById((Long) any());
        verify(messageChannel).send((Message<Object>) any());
    }


    /**
     * Method under test: {@link ConversationController#archive(Long)}
     */
    @Test
    void testArchive2() {
        // Arrange
        Conversation conversation = new Conversation();
        conversation.setSupport(new Support());
        Optional<Conversation> ofResult = Optional.of(conversation);
        ConversationRepository conversationRepository = mock(ConversationRepository.class);
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any()))
                .thenReturn(new ArrayList<>());
        when(conversationRepository.save((Conversation) any())).thenReturn(new Conversation());
        when(conversationRepository.findById((Long) any())).thenReturn(ofResult);
        SupportRepository supportRepository = mock(SupportRepository.class);
        when(supportRepository.save((Support) any())).thenReturn(new Support());
        when(supportRepository.findById((Long) any())).thenReturn(Optional.of(new Support()));
        SupportServiceImpl supportService = new SupportServiceImpl(supportRepository);
        MessageChannel messageChannel = mock(MessageChannel.class);
        when(messageChannel.send((Message<Object>) any())).thenReturn(true);
        SimpMessagingTemplate messagingTemplate = new SimpMessagingTemplate(messageChannel);
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        DoctorServiceImpl doctorService = new DoctorServiceImpl(doctorRepository,
                new AdminServiceImpl(mock(AdminRepository.class)));

        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        // Act
        ResponseEntity<Conversation> actualArchiveResult = (new ConversationController(
                new ConversationServiceImpl(conversationRepository, doctorService, supportService,
                        new NotificationServiceImpl(notificationRepository, userRepository,
                                new SimpMessagingTemplate(mock(MessageChannel.class))),
                        mock(MessageRepository.class), messagingTemplate))).archive(123L);

        // Assert
        assertTrue(actualArchiveResult.hasBody());
        assertTrue(actualArchiveResult.getHeaders().isEmpty());
        assertEquals(200, actualArchiveResult.getStatusCodeValue());
        assertTrue(actualArchiveResult.getBody().isArchived());
        verify(conversationRepository).save((Conversation) any());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any());
        verify(conversationRepository).findById((Long) any());
        verify(supportRepository).save((Support) any());
        verify(supportRepository, atLeast(1)).findById((Long) any());
        verify(messageChannel).send((Message<Object>) any());
    }

    /**
     * Method under test: {@link ConversationController#getConversationByDoctorId(Long)}
     */
    @Test
    void testGetConversationByDoctorId() {
        // Arrange
        ConversationRepository conversationRepository = mock(ConversationRepository.class);
        when(conversationRepository.findConversationByArchivedIsFalseAndAndDoctorId((Long) any()))
                .thenReturn(new Conversation());
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        DoctorServiceImpl doctorService = new DoctorServiceImpl(doctorRepository,
                new AdminServiceImpl(mock(AdminRepository.class)));

        SupportServiceImpl supportService = new SupportServiceImpl(mock(SupportRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationRepository, userRepository,
                new SimpMessagingTemplate(mock(MessageChannel.class)));

        MessageRepository messageService = mock(MessageRepository.class);

        // Act
        ResponseEntity<?> actualConversationByDoctorId = (new ConversationController(
                new ConversationServiceImpl(conversationRepository, doctorService, supportService, notificationService,
                        messageService, new SimpMessagingTemplate(mock(MessageChannel.class))))).getConversationByDoctorId(123L);

        // Assert
        assertTrue(actualConversationByDoctorId.hasBody());
        assertEquals(200, actualConversationByDoctorId.getStatusCodeValue());
        assertTrue(actualConversationByDoctorId.getHeaders().isEmpty());
        verify(conversationRepository).findConversationByArchivedIsFalseAndAndDoctorId((Long) any());
    }

    /**
     * Method under test: {@link ConversationController#getConversationByDoctorId(Long)}
     */
    @Test
    void testGetConversationByDoctorId2() {
        // Arrange
        ConversationRepository conversationRepository = mock(ConversationRepository.class);
        when(conversationRepository.findConversationByArchivedIsFalseAndAndDoctorId((Long) any())).thenReturn(null);
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        DoctorServiceImpl doctorService = new DoctorServiceImpl(doctorRepository,
                new AdminServiceImpl(mock(AdminRepository.class)));

        SupportServiceImpl supportService = new SupportServiceImpl(mock(SupportRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationRepository, userRepository,
                new SimpMessagingTemplate(mock(MessageChannel.class)));

        MessageRepository messageService = mock(MessageRepository.class);

        // Act
        ResponseEntity<?> actualConversationByDoctorId = (new ConversationController(
                new ConversationServiceImpl(conversationRepository, doctorService, supportService, notificationService,
                        messageService, new SimpMessagingTemplate(mock(MessageChannel.class))))).getConversationByDoctorId(123L);

        // Assert
        assertNull(actualConversationByDoctorId.getBody());
        assertEquals(404, actualConversationByDoctorId.getStatusCodeValue());
        assertTrue(actualConversationByDoctorId.getHeaders().isEmpty());
        verify(conversationRepository).findConversationByArchivedIsFalseAndAndDoctorId((Long) any());
    }


}

