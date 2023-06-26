package tn.corilus.authentification.authentification.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.corilus.authentification.authentification.entities.Conversation;
import tn.corilus.authentification.authentification.entities.Support;
import tn.corilus.authentification.authentification.repository.ConversationRepository;
import tn.corilus.authentification.authentification.repository.MessageRepository;

@ContextConfiguration(classes = {ConversationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ConversationServiceImplTest {
    @MockBean
    private ConversationRepository conversationRepository;

    @Autowired
    private ConversationServiceImpl conversationServiceImpl;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;

    @MockBean
    private SupportService supportService;



    /**
     * Method under test: {@link ConversationServiceImpl#archiveConversation(Long)}
     */
    @Test
    void testArchiveConversation2() {
        // Arrange
        when(conversationRepository.save((Conversation) any()))
                .thenThrow(new EntityNotFoundException("An error occurred"));
        when(conversationRepository.findById((Long) any())).thenReturn(Optional.of(new Conversation()));

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> conversationServiceImpl.archiveConversation(123L));
        verify(conversationRepository).save((Conversation) any());
        verify(conversationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ConversationServiceImpl#archiveConversation(Long)}
     */
    @Test
    void testArchiveConversation3() throws MessagingException {
        // Arrange
        Support support = new Support();
        support.setId(123L);

        Conversation conversation = new Conversation();
        conversation.setSupport(support);
        Optional<Conversation> ofResult = Optional.of(conversation);
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any()))
                .thenReturn(new ArrayList<>());
        when(conversationRepository.save((Conversation) any())).thenReturn(new Conversation());
        when(conversationRepository.findById((Long) any())).thenReturn(ofResult);
        when(supportService.modifySupport((Support) any())).thenReturn(new Support());
        when(supportService.getSupport((Long) any())).thenReturn(new Support());
        doNothing().when(simpMessagingTemplate).convertAndSend((String) any(), (Object) any());

        // Act
        Conversation actualArchiveConversationResult = conversationServiceImpl.archiveConversation(123L);

        // Assert
        assertSame(conversation, actualArchiveConversationResult);
        assertTrue(actualArchiveConversationResult.isArchived());
        verify(conversationRepository).save((Conversation) any());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any());
        verify(conversationRepository).findById((Long) any());
        verify(supportService).getSupport((Long) any());
        verify(supportService).modifySupport((Support) any());
        verify(simpMessagingTemplate).convertAndSend((String) any(), (Object) any());
    }

    /**
     * Method under test: {@link ConversationServiceImpl#archiveConversation(Long)}
     */
    @Test
    void testArchiveConversation4() throws MessagingException {
        // Arrange
        Support support = new Support();
        support.setId(123L);

        Conversation conversation = new Conversation();
        conversation.setSupport(support);
        Optional<Conversation> ofResult = Optional.of(conversation);
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any()))
                .thenReturn(new ArrayList<>());
        when(conversationRepository.save((Conversation) any())).thenReturn(new Conversation());
        when(conversationRepository.findById((Long) any())).thenReturn(ofResult);
        when(supportService.modifySupport((Support) any())).thenReturn(new Support());
        when(supportService.getSupport((Long) any())).thenReturn(new Support());
        doThrow(new EntityNotFoundException("An error occurred")).when(simpMessagingTemplate)
                .convertAndSend((String) any(), (Object) any());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> conversationServiceImpl.archiveConversation(123L));
        verify(conversationRepository).save((Conversation) any());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any());
        verify(conversationRepository).findById((Long) any());
        verify(simpMessagingTemplate).convertAndSend((String) any(), (Object) any());
    }


}

