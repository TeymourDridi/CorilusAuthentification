package tn.corilus.authentification.authentification.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.entities.Conversation;
import tn.corilus.authentification.authentification.entities.Doctor;
import tn.corilus.authentification.authentification.entities.Message;
import tn.corilus.authentification.authentification.entities.Notification;
import tn.corilus.authentification.authentification.entities.Support;
import tn.corilus.authentification.authentification.entities.User;
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
import tn.corilus.authentification.authentification.service.MessageService;
import tn.corilus.authentification.authentification.service.NotificationServiceImpl;
import tn.corilus.authentification.authentification.service.SupportServiceImpl;

class ChatControllerTest {


    /**
     * Method under test: {@link ChatController#createConversation(Long)}
     */
    @Test
    void testCreateConversation2() {
        // Arrange
        ArrayList<Conversation> conversationList = new ArrayList<>();
        conversationList.add(new Conversation());
        ConversationRepository conversationRepository = mock(ConversationRepository.class);
        when(conversationRepository.findConversationByArchivedIsFalseAndAndDoctorId((Long) any()))
                .thenReturn(conversationList);
        when(conversationRepository.save((Conversation) any())).thenReturn(new Conversation());
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any()))
                .thenReturn(new ArrayList<>());
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        Doctor doctor = new Doctor();
        when(doctorRepository.findById((Long) any())).thenReturn(Optional.of(doctor));
        DoctorServiceImpl doctorService = new DoctorServiceImpl(doctorRepository,
                new AdminServiceImpl(mock(AdminRepository.class)));

        SupportRepository supportRepository = mock(SupportRepository.class);
        Support support = new Support();
        when(supportRepository.findSupportWithLeastConversations()).thenReturn(support);
        SupportServiceImpl supportService = new SupportServiceImpl(supportRepository);
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        when(notificationRepository.save((Notification) any())).thenReturn(new Notification());
        when(notificationRepository.findAllByUser((User) any())).thenReturn(new ArrayList<>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new Admin()));
        MessageChannel messageChannel = mock(MessageChannel.class);
        when(messageChannel.send((org.springframework.messaging.Message<Object>) any())).thenReturn(true);
        NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationRepository, userRepository,
                new SimpMessagingTemplate(messageChannel));

        MessageRepository messageRepository = mock(MessageRepository.class);
        when(messageRepository.save((tn.corilus.authentification.authentification.entities.Message) any()))
                .thenReturn(new tn.corilus.authentification.authentification.entities.Message());
        MessageChannel messageChannel1 = mock(MessageChannel.class);
        when(messageChannel1.send((org.springframework.messaging.Message<Object>) any())).thenReturn(true);

        // Act
        Conversation actualCreateConversationResult = (new ChatController(
                new ConversationServiceImpl(conversationRepository, doctorService, supportService, notificationService,
                        messageRepository, new SimpMessagingTemplate(messageChannel1)),
                mock(MessageService.class))).createConversation(123L);

        // Assert
        assertSame(doctor, actualCreateConversationResult.getDoctor());
        assertFalse(actualCreateConversationResult.isArchived());
        assertEquals(1, actualCreateConversationResult.getMessages().size());
        assertSame(support, actualCreateConversationResult.getSupport());
        verify(conversationRepository).save((Conversation) any());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any());
        verify(conversationRepository).findConversationByArchivedIsFalseAndAndDoctorId((Long) any());
        verify(doctorRepository).findById((Long) any());
        verify(supportRepository).findSupportWithLeastConversations();
        verify(notificationRepository).save((Notification) any());
        verify(notificationRepository).findAllByUser((User) any());
        verify(userRepository, atLeast(1)).findById((Long) any());
        verify(messageChannel).send((org.springframework.messaging.Message<Object>) any());
        verify(messageRepository).save((tn.corilus.authentification.authentification.entities.Message) any());
        verify(messageChannel1, atLeast(1)).send((org.springframework.messaging.Message<Object>) any());
    }


    /**
     * Method under test: {@link ChatController#sendMessage(Long, boolean, Message)}
     */
    @Test
    void testSendMessage2() {
        // Arrange
        ConversationRepository conversationRepository = mock(ConversationRepository.class);
        when(conversationRepository.findById((Long) any())).thenReturn(Optional.of(new Conversation()));
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        DoctorServiceImpl doctorService = new DoctorServiceImpl(doctorRepository,
                new AdminServiceImpl(mock(AdminRepository.class)));

        SupportServiceImpl supportService = new SupportServiceImpl(mock(SupportRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationRepository, userRepository,
                new SimpMessagingTemplate(mock(MessageChannel.class)));

        MessageRepository messageService = mock(MessageRepository.class);
        ConversationServiceImpl conversationService = new ConversationServiceImpl(conversationRepository, doctorService,
                supportService, notificationService, messageService, new SimpMessagingTemplate(mock(MessageChannel.class)));

        MessageService messageService1 = mock(MessageService.class);
        Message message = new Message();
        when(messageService1.createMessage((Conversation) any(), (User) any(), (String) any())).thenReturn(message);
        ChatController chatController = new ChatController(conversationService, messageService1);

        // Act and Assert
        assertSame(message, chatController.sendMessage(123L, true, new Message()));
        verify(conversationRepository).findById((Long) any());
        verify(messageService1).createMessage((Conversation) any(), (User) any(), (String) any());
    }

    /**
     * Method under test: {@link ChatController#sendMessage(Long, boolean, Message)}
     */
    @Test
    void testSendMessage5() {
        // Arrange
        ConversationRepository conversationRepository = mock(ConversationRepository.class);
        when(conversationRepository.findById((Long) any())).thenReturn(Optional.of(new Conversation()));
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        DoctorServiceImpl doctorService = new DoctorServiceImpl(doctorRepository,
                new AdminServiceImpl(mock(AdminRepository.class)));

        SupportServiceImpl supportService = new SupportServiceImpl(mock(SupportRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationRepository, userRepository,
                new SimpMessagingTemplate(mock(MessageChannel.class)));

        MessageRepository messageService = mock(MessageRepository.class);
        ConversationServiceImpl conversationService = new ConversationServiceImpl(conversationRepository, doctorService,
                supportService, notificationService, messageService, new SimpMessagingTemplate(mock(MessageChannel.class)));

        MessageService messageService1 = mock(MessageService.class);
        Message message = new Message();
        when(messageService1.createMessage((Conversation) any(), (User) any(), (String) any())).thenReturn(message);
        ChatController chatController = new ChatController(conversationService, messageService1);

        // Act and Assert
        assertSame(message, chatController.sendMessage(123L, false, new Message()));
        verify(conversationRepository).findById((Long) any());
        verify(messageService1).createMessage((Conversation) any(), (User) any(), (String) any());
    }

}

