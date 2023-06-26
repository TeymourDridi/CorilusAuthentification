package tn.corilus.authentification.authentification.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

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
import tn.corilus.authentification.authentification.entities.Doctor;
import tn.corilus.authentification.authentification.entities.Message;
import tn.corilus.authentification.authentification.entities.Notification;
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
     * Method under test: {@link ConversationServiceImpl#createConversation(Doctor)}
     */
    @Test
    void testCreateConversation() throws MessagingException {
        // Arrange
        when(conversationRepository.save((Conversation) any())).thenReturn(new Conversation());
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any()))
                .thenReturn(new ArrayList<>());
        when(conversationRepository.findConversationByArchivedIsFalseAndAndDoctorId((Long) any()))
                .thenReturn(new Conversation());
        Doctor doctor = new Doctor();
        when(doctorService.getDoctor((Long) any())).thenReturn(doctor);
        Support support = new Support();
        when(supportService.getSupportWithLeastConversations()).thenReturn(support);
        when(notificationService.addNotification((String) any(), (Long) any())).thenReturn(new Notification());
        when(messageRepository.save((Message) any())).thenReturn(new Message());
        doNothing().when(simpMessagingTemplate).convertAndSend((String) any(), (Object) any());

        // Act
        Conversation actualCreateConversationResult = conversationServiceImpl.createConversation(new Doctor());

        // Assert
        assertSame(doctor, actualCreateConversationResult.getDoctor());
        assertFalse(actualCreateConversationResult.isArchived());
        assertEquals(1, actualCreateConversationResult.getMessages().size());
        assertSame(support, actualCreateConversationResult.getSupport());
        verify(conversationRepository).save((Conversation) any());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any());
        verify(conversationRepository).findConversationByArchivedIsFalseAndAndDoctorId((Long) any());
        verify(doctorService).getDoctor((Long) any());
        verify(supportService).getSupportWithLeastConversations();
        verify(notificationService).addNotification((String) any(), (Long) any());
        verify(messageRepository).save((Message) any());
        verify(simpMessagingTemplate, atLeast(1)).convertAndSend((String) any(), (Object) any());
    }

    /**
     * Method under test: {@link ConversationServiceImpl#createConversation(Doctor)}
     */
    @Test
    void testCreateConversation2() throws MessagingException {
        // Arrange
        when(conversationRepository.save((Conversation) any())).thenReturn(new Conversation());
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any()))
                .thenReturn(new ArrayList<>());
        when(conversationRepository.findConversationByArchivedIsFalseAndAndDoctorId((Long) any()))
                .thenReturn(new Conversation());
        when(doctorService.getDoctor((Long) any())).thenReturn(new Doctor());
        when(supportService.getSupportWithLeastConversations()).thenReturn(new Support());
        when(notificationService.addNotification((String) any(), (Long) any())).thenReturn(new Notification());
        when(messageRepository.save((Message) any())).thenReturn(new Message());
        doThrow(new EntityNotFoundException("An error occurred")).when(simpMessagingTemplate)
                .convertAndSend((String) any(), (Object) any());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> conversationServiceImpl.createConversation(new Doctor()));
        verify(conversationRepository).save((Conversation) any());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any());
        verify(doctorService).getDoctor((Long) any());
        verify(supportService).getSupportWithLeastConversations();
        verify(messageRepository).save((Message) any());
        verify(simpMessagingTemplate).convertAndSend((String) any(), (Object) any());
    }

    /**
     * Method under test: {@link ConversationServiceImpl#createConversation(Doctor)}
     */
    @Test
    void testCreateConversation3() throws MessagingException {
        // Arrange
        when(conversationRepository.save((Conversation) any())).thenReturn(new Conversation());
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any()))
                .thenReturn(new ArrayList<>());
        when(conversationRepository.findConversationByArchivedIsFalseAndAndDoctorId((Long) any())).thenReturn(null);
        Doctor doctor = new Doctor();
        when(doctorService.getDoctor((Long) any())).thenReturn(doctor);
        Support support = new Support();
        when(supportService.getSupportWithLeastConversations()).thenReturn(support);
        when(notificationService.addNotification((String) any(), (Long) any())).thenReturn(new Notification());
        when(messageRepository.save((Message) any())).thenReturn(new Message());
        doNothing().when(simpMessagingTemplate).convertAndSend((String) any(), (Object) any());

        // Act
        Conversation actualCreateConversationResult = conversationServiceImpl.createConversation(new Doctor());

        // Assert
        assertSame(doctor, actualCreateConversationResult.getDoctor());
        assertFalse(actualCreateConversationResult.isArchived());
        assertEquals(1, actualCreateConversationResult.getMessages().size());
        assertSame(support, actualCreateConversationResult.getSupport());
        verify(conversationRepository).save((Conversation) any());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any());
        verify(conversationRepository).findConversationByArchivedIsFalseAndAndDoctorId((Long) any());
        verify(doctorService).getDoctor((Long) any());
        verify(supportService).getSupportWithLeastConversations();
        verify(notificationService).addNotification((String) any(), (Long) any());
        verify(messageRepository).save((Message) any());
        verify(simpMessagingTemplate, atLeast(1)).convertAndSend((String) any(), (Object) any());
    }



    /**
     * Method under test: {@link ConversationServiceImpl#getConversationById(Long)}
     */
    @Test
    void testGetConversationById() {
        // Arrange
        Conversation conversation = new Conversation();
        when(conversationRepository.findById((Long) any())).thenReturn(Optional.of(conversation));

        // Act and Assert
        assertSame(conversation, conversationServiceImpl.getConversationById(123L));
        verify(conversationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ConversationServiceImpl#getConversationById(Long)}
     */
    @Test
    void testGetConversationById2() {
        // Arrange
        when(conversationRepository.findById((Long) any())).thenThrow(new EntityNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> conversationServiceImpl.getConversationById(123L));
        verify(conversationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ConversationServiceImpl#getConversationBySupportIdAndArchivedIsFalse(Long)}
     */
    @Test
    void testGetConversationBySupportIdAndArchivedIsFalse() {
        // Arrange
        ArrayList<Conversation> conversationList = new ArrayList<>();
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any()))
                .thenReturn(conversationList);

        // Act
        List<Conversation> actualConversationBySupportIdAndArchivedIsFalse = conversationServiceImpl
                .getConversationBySupportIdAndArchivedIsFalse(123L);

        // Assert
        assertSame(conversationList, actualConversationBySupportIdAndArchivedIsFalse);
        assertTrue(actualConversationBySupportIdAndArchivedIsFalse.isEmpty());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any());
    }

    /**
     * Method under test: {@link ConversationServiceImpl#getConversationBySupportIdAndArchivedIsFalse(Long)}
     */
    @Test
    void testGetConversationBySupportIdAndArchivedIsFalse2() {
        // Arrange
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any()))
                .thenThrow(new EntityNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(EntityNotFoundException.class,
                () -> conversationServiceImpl.getConversationBySupportIdAndArchivedIsFalse(123L));
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any());
    }


    /**
     * Method under test: {@link ConversationServiceImpl#markAllMessagesAsReaded(Long)}
     */
    @Test
    void testMarkAllMessagesAsReaded2() {
        // Arrange
        when(conversationRepository.save((Conversation) any()))
                .thenThrow(new EntityNotFoundException("An error occurred"));
        when(conversationRepository.findById((Long) any())).thenReturn(Optional.of(new Conversation()));

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> conversationServiceImpl.markAllMessagesAsReaded(123L));
        verify(conversationRepository).save((Conversation) any());
        verify(conversationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ConversationServiceImpl#markAllMessagesAsReaded(Long)}
     */
    @Test
    void testMarkAllMessagesAsReaded3() throws MessagingException {
        // Arrange
        Conversation conversation = new Conversation();
        conversation.setSupport(new Support());
        Optional<Conversation> ofResult = Optional.of(conversation);
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any()))
                .thenReturn(new ArrayList<>());
        when(conversationRepository.save((Conversation) any())).thenReturn(new Conversation());
        when(conversationRepository.findById((Long) any())).thenReturn(ofResult);
        doNothing().when(simpMessagingTemplate).convertAndSend((String) any(), (Object) any());

        // Act
        conversationServiceImpl.markAllMessagesAsReaded(123L);

        // Assert
        verify(conversationRepository).save((Conversation) any());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any());
        verify(conversationRepository).findById((Long) any());
        verify(simpMessagingTemplate).convertAndSend((String) any(), (Object) any());
    }

    /**
     * Method under test: {@link ConversationServiceImpl#markAllMessagesAsReaded(Long)}
     */
    @Test
    void testMarkAllMessagesAsReaded4() throws MessagingException {
        // Arrange
        Conversation conversation = new Conversation();
        conversation.setSupport(new Support());
        Optional<Conversation> ofResult = Optional.of(conversation);
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any()))
                .thenReturn(new ArrayList<>());
        when(conversationRepository.save((Conversation) any())).thenReturn(new Conversation());
        when(conversationRepository.findById((Long) any())).thenReturn(ofResult);
        doThrow(new EntityNotFoundException("An error occurred")).when(simpMessagingTemplate)
                .convertAndSend((String) any(), (Object) any());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> conversationServiceImpl.markAllMessagesAsReaded(123L));
        verify(conversationRepository).save((Conversation) any());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any());
        verify(conversationRepository).findById((Long) any());
        verify(simpMessagingTemplate).convertAndSend((String) any(), (Object) any());
    }

    /**
     * Method under test: {@link ConversationServiceImpl#markAllMessagesAsReaded(Long)}
     */
    @Test
    void testMarkAllMessagesAsReaded5() throws MessagingException {
        // Arrange
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc((Long) any()))
                .thenReturn(new ArrayList<>());
        when(conversationRepository.save((Conversation) any())).thenReturn(new Conversation());
        when(conversationRepository.findById((Long) any())).thenReturn(Optional.empty());
        doNothing().when(simpMessagingTemplate).convertAndSend((String) any(), (Object) any());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> conversationServiceImpl.markAllMessagesAsReaded(123L));
        verify(conversationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ConversationServiceImpl#getConversationByDoctorIdAndArchivedIsFalse(Long)}
     */
    @Test
    void testGetConversationByDoctorIdAndArchivedIsFalse() {
        // Arrange
        Conversation conversation = new Conversation();
        when(conversationRepository.findConversationByArchivedIsFalseAndAndDoctorId((Long) any()))
                .thenReturn(conversation);

        // Act and Assert
        assertSame(conversation, conversationServiceImpl.getConversationByDoctorIdAndArchivedIsFalse(123L));
        verify(conversationRepository).findConversationByArchivedIsFalseAndAndDoctorId((Long) any());
    }

    /**
     * Method under test: {@link ConversationServiceImpl#getConversationByDoctorIdAndArchivedIsFalse(Long)}
     */
    @Test
    void testGetConversationByDoctorIdAndArchivedIsFalse2() {
        // Arrange
        when(conversationRepository.findConversationByArchivedIsFalseAndAndDoctorId((Long) any())).thenReturn(null);

        // Act and Assert
        assertNull(conversationServiceImpl.getConversationByDoctorIdAndArchivedIsFalse(123L));
        verify(conversationRepository).findConversationByArchivedIsFalseAndAndDoctorId((Long) any());
    }

    /**
     * Method under test: {@link ConversationServiceImpl#getConversationByDoctorIdAndArchivedIsFalse(Long)}
     */
    @Test
    void testGetConversationByDoctorIdAndArchivedIsFalse3() {
        // Arrange
        when(conversationRepository.findConversationByArchivedIsFalseAndAndDoctorId((Long) any()))
                .thenThrow(new EntityNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(EntityNotFoundException.class,
                () -> conversationServiceImpl.getConversationByDoctorIdAndArchivedIsFalse(123L));
        verify(conversationRepository).findConversationByArchivedIsFalseAndAndDoctorId((Long) any());
    }



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


    /**
     * Method under test: {@link ConversationServiceImpl#getConversationsBySupportIdAndArchivedIsTrue(Long)}
     */
    @Test
    void testGetConversationsBySupportIdAndArchivedIsTrue() {
        // Arrange
        ArrayList<Conversation> conversationList = new ArrayList<>();
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsTrueOrderByLastUpdatedAtDesc((Long) any()))
                .thenReturn(conversationList);

        // Act
        List<Conversation> actualConversationsBySupportIdAndArchivedIsTrue = conversationServiceImpl
                .getConversationsBySupportIdAndArchivedIsTrue(123L);

        // Assert
        assertSame(conversationList, actualConversationsBySupportIdAndArchivedIsTrue);
        assertTrue(actualConversationsBySupportIdAndArchivedIsTrue.isEmpty());
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsTrueOrderByLastUpdatedAtDesc((Long) any());
    }

    /**
     * Method under test: {@link ConversationServiceImpl#getConversationsBySupportIdAndArchivedIsTrue(Long)}
     */
    @Test
    void testGetConversationsBySupportIdAndArchivedIsTrue2() {
        // Arrange
        when(conversationRepository.findAllBySupportIdAndAndArchivedIsTrueOrderByLastUpdatedAtDesc((Long) any()))
                .thenThrow(new EntityNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(EntityNotFoundException.class,
                () -> conversationServiceImpl.getConversationsBySupportIdAndArchivedIsTrue(123L));
        verify(conversationRepository).findAllBySupportIdAndAndArchivedIsTrueOrderByLastUpdatedAtDesc((Long) any());
    }
}

