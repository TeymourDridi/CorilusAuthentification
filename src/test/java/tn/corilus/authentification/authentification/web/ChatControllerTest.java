package tn.corilus.authentification.authentification.web;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import tn.corilus.authentification.authentification.entities.Conversation;
import tn.corilus.authentification.authentification.entities.Doctor;
import tn.corilus.authentification.authentification.entities.Message;
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
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        when(doctorRepository.findById((Long) any())).thenReturn(Optional.of(new Doctor()));
        DoctorServiceImpl doctorService = new DoctorServiceImpl(doctorRepository,
                new AdminServiceImpl(mock(AdminRepository.class)));

        SupportRepository supportRepository = mock(SupportRepository.class);
        when(supportRepository.findSupportWithLeastConversations()).thenReturn(new ArrayList<>());
        SupportServiceImpl supportService = new SupportServiceImpl(supportRepository);
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationRepository, userRepository,
                new SimpMessagingTemplate(mock(MessageChannel.class)));

        MessageRepository messageService = mock(MessageRepository.class);

        // Act and Assert
        assertNull((new ChatController(new ConversationServiceImpl(conversationRepository, doctorService, supportService,
                notificationService, messageService, new SimpMessagingTemplate(mock(MessageChannel.class))),
                mock(MessageService.class))).createConversation(1L));
        verify(conversationRepository).findConversationByArchivedIsFalseAndAndDoctorId((Long) any());
    }



}

