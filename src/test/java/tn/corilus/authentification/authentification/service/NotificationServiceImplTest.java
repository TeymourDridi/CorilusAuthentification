package tn.corilus.authentification.authentification.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.entities.Notification;
import tn.corilus.authentification.authentification.entities.User;
import tn.corilus.authentification.authentification.repository.NotificationRepository;
import tn.corilus.authentification.authentification.repository.UserRepository;

@ContextConfiguration(classes = {NotificationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class NotificationServiceImplTest {
    @MockBean
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link NotificationServiceImpl#addNotification(String, Long)}
     */
    @Test
    void testAddNotification() throws MessagingException {
        // Arrange
        when(notificationRepository.save((Notification) any())).thenReturn(new Notification());
        when(notificationRepository.findAllByUser((User) any())).thenReturn(new ArrayList<>());
        Admin admin = new Admin();
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(admin));
        doNothing().when(simpMessagingTemplate).convertAndSend((String) any(), (Object) any());

        // Act
        Notification actualAddNotificationResult = notificationServiceImpl.addNotification("Not all who wander are lost",
                1L);

        // Assert
        assertSame(admin, actualAddNotificationResult.getUser());
        assertEquals("Not all who wander are lost", actualAddNotificationResult.getMessage());
        verify(notificationRepository).save((Notification) any());
        verify(notificationRepository).findAllByUser((User) any());
        verify(userRepository, atLeast(1)).findById((Long) any());
        verify(simpMessagingTemplate).convertAndSend((String) any(), (Object) any());
    }

    /**
     * Method under test: {@link NotificationServiceImpl#getAllNotificationsByUser(Long)}
     */
    @Test
    void testGetAllNotificationsByUser() {
        // Arrange
        ArrayList<Notification> notificationList = new ArrayList<>();
        when(notificationRepository.findAllByUser((User) any())).thenReturn(notificationList);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new Admin()));

        // Act
        List<Notification> actualAllNotificationsByUser = notificationServiceImpl.getAllNotificationsByUser(1L);

        // Assert
        assertSame(notificationList, actualAllNotificationsByUser);
        assertTrue(actualAllNotificationsByUser.isEmpty());
        verify(notificationRepository).findAllByUser((User) any());
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link NotificationServiceImpl#notificationReaded(Long)}
     */
    @Test
    void testNotificationReaded() throws MessagingException {
        // Arrange
        when(userRepository.save((Admin) any())).thenReturn(new Admin());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new Admin()));
        doNothing().when(simpMessagingTemplate).convertAndSend((String) any(), (Object) any());

        // Act and Assert
        assertTrue(notificationServiceImpl.notificationReaded(1L).isEmpty());
        verify(userRepository).save((Admin) any());
        verify(userRepository).findById((Long) any());
        verify(simpMessagingTemplate).convertAndSend((String) any(), (Object) any());
    }


}

