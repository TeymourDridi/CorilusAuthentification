package tn.corilus.authentification.authentification.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.entities.Notification;
import tn.corilus.authentification.authentification.entities.User;
import tn.corilus.authentification.authentification.repository.NotificationRepository;
import tn.corilus.authentification.authentification.repository.UserRepository;
import tn.corilus.authentification.authentification.service.NotificationServiceImpl;

class NotificationControllerTest {
    /**
     * Method under test: {@link NotificationController#markAllMessagesAsReaded(Long)}
     */
    @Test
    void testMarkAllMessagesAsReaded() {

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((Admin) any())).thenReturn(new Admin());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new Admin()));
        MessageChannel messageChannel = mock(MessageChannel.class);
        when(messageChannel.send((Message<Object>) any())).thenReturn(true);

        // Act
        ResponseEntity<String> actualMarkAllMessagesAsReadedResult = (new NotificationController(
                new NotificationServiceImpl(mock(NotificationRepository.class), userRepository,
                        new SimpMessagingTemplate(messageChannel)))).markAllMessagesAsReaded(123L);

        // Assert
        assertEquals("All notifications marked as readed", actualMarkAllMessagesAsReadedResult.getBody());
        assertEquals(200, actualMarkAllMessagesAsReadedResult.getStatusCodeValue());
        assertTrue(actualMarkAllMessagesAsReadedResult.getHeaders().isEmpty());
        verify(userRepository).save((Admin) any());
        verify(userRepository).findById((Long) any());
        verify(messageChannel).send((Message<Object>) any());
    }


    /**
     * Method under test: {@link NotificationController#getAllNotification(Long)}
     */
    @Test
    void testGetAllNotification() {

        // Arrange
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        when(notificationRepository.findAllByUser((User) any())).thenReturn(new ArrayList<>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new Admin()));

        // Act
        ResponseEntity<List<Notification>> actualAllNotification = (new NotificationController(new NotificationServiceImpl(
                notificationRepository, userRepository, new SimpMessagingTemplate(mock(MessageChannel.class)))))
                .getAllNotification(123L);

        // Assert
        assertTrue(actualAllNotification.hasBody());
        assertEquals(200, actualAllNotification.getStatusCodeValue());
        assertTrue(actualAllNotification.getHeaders().isEmpty());
        verify(notificationRepository).findAllByUser((User) any());
        verify(userRepository).findById((Long) any());
    }


}

