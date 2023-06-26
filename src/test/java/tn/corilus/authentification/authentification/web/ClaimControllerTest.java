package tn.corilus.authentification.authentification.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.entities.Claim;
import tn.corilus.authentification.authentification.entities.Notification;
import tn.corilus.authentification.authentification.entities.Support;
import tn.corilus.authentification.authentification.entities.User;
import tn.corilus.authentification.authentification.repository.AdminRepository;
import tn.corilus.authentification.authentification.repository.ClaimRepository;
import tn.corilus.authentification.authentification.repository.NotificationRepository;
import tn.corilus.authentification.authentification.repository.UserRepository;
import tn.corilus.authentification.authentification.service.AdminService;
import tn.corilus.authentification.authentification.service.AdminServiceImpl;
import tn.corilus.authentification.authentification.service.ClaimServiceImpl;
import tn.corilus.authentification.authentification.service.NotificationServiceImpl;

class ClaimControllerTest {
    /**
     * Method under test: {@link ClaimController#getAllClaimsByAdminIdAndAcceptedIsNull(Long)}
     */
    @Test
    void testGetAllClaimsByAdminIdAndAcceptedIsNull() {
        // Arrange
        ClaimRepository claimRepository = mock(ClaimRepository.class);
        when(claimRepository.findAllByAdminIdAndTreatedIsFalse((Long) any())).thenReturn(new ArrayList<>());
        AdminServiceImpl adminService = new AdminServiceImpl(mock(AdminRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        // Act
        ResponseEntity<List<Claim>> actualAllClaimsByAdminIdAndAcceptedIsNull = (new ClaimController(
                new ClaimServiceImpl(claimRepository, adminService, new NotificationServiceImpl(notificationRepository,
                        userRepository, new SimpMessagingTemplate(mock(MessageChannel.class))))))
                .getAllClaimsByAdminIdAndAcceptedIsNull(123L);

        // Assert
        assertTrue(actualAllClaimsByAdminIdAndAcceptedIsNull.hasBody());
        assertEquals(200, actualAllClaimsByAdminIdAndAcceptedIsNull.getStatusCodeValue());
        assertTrue(actualAllClaimsByAdminIdAndAcceptedIsNull.getHeaders().isEmpty());
        verify(claimRepository).findAllByAdminIdAndTreatedIsFalse((Long) any());
    }


    /**
     * Method under test: {@link ClaimController#getAllClaimsByAdminIdAndAcceptedIsTrue(Long)}
     */
    @Test
    void testGetAllClaimsByAdminIdAndAcceptedIsTrue() {
        // Arrange
        ClaimRepository claimRepository = mock(ClaimRepository.class);
        when(claimRepository.findAllByAdminIdAndAcceptedIsTrue((Long) any())).thenReturn(new ArrayList<>());
        AdminServiceImpl adminService = new AdminServiceImpl(mock(AdminRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        // Act
        ResponseEntity<List<Claim>> actualAllClaimsByAdminIdAndAcceptedIsTrue = (new ClaimController(
                new ClaimServiceImpl(claimRepository, adminService,
                        new NotificationServiceImpl(notificationRepository, userRepository,
                                new SimpMessagingTemplate(mock(MessageChannel.class))))))
                .getAllClaimsByAdminIdAndAcceptedIsTrue(123L);

        // Assert
        assertTrue(actualAllClaimsByAdminIdAndAcceptedIsTrue.hasBody());
        assertEquals(200, actualAllClaimsByAdminIdAndAcceptedIsTrue.getStatusCodeValue());
        assertTrue(actualAllClaimsByAdminIdAndAcceptedIsTrue.getHeaders().isEmpty());
        verify(claimRepository).findAllByAdminIdAndAcceptedIsTrue((Long) any());
    }


    /**
     * Method under test: {@link ClaimController#getAllClaimsByAdminIdAndAcceptedIsFalse(Long)}
     */
    @Test
    void testGetAllClaimsByAdminIdAndAcceptedIsFalse() {

        // Arrange
        ClaimRepository claimRepository = mock(ClaimRepository.class);
        when(claimRepository.findAllByAdminIdAndAcceptedIsFalseAndTreatedIsTrue((Long) any()))
                .thenReturn(new ArrayList<>());
        AdminServiceImpl adminService = new AdminServiceImpl(mock(AdminRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        // Act
        ResponseEntity<List<Claim>> actualAllClaimsByAdminIdAndAcceptedIsFalse = (new ClaimController(
                new ClaimServiceImpl(claimRepository, adminService, new NotificationServiceImpl(notificationRepository,
                        userRepository, new SimpMessagingTemplate(mock(MessageChannel.class))))))
                .getAllClaimsByAdminIdAndAcceptedIsFalse(123L);

        // Assert
        assertTrue(actualAllClaimsByAdminIdAndAcceptedIsFalse.hasBody());
        assertEquals(200, actualAllClaimsByAdminIdAndAcceptedIsFalse.getStatusCodeValue());
        assertTrue(actualAllClaimsByAdminIdAndAcceptedIsFalse.getHeaders().isEmpty());
        verify(claimRepository).findAllByAdminIdAndAcceptedIsFalseAndTreatedIsTrue((Long) any());
    }


    /**
     * Method under test: {@link ClaimController#getAllClaimsBySupportIdAndAcceptedIsFalse(Long)}
     */
    @Test
    void testGetAllClaimsBySupportIdAndAcceptedIsFalse() {
        // Arrange
        ClaimRepository claimRepository = mock(ClaimRepository.class);
        when(claimRepository.findAllBySupportIdAndAcceptedIsFalseAndTreatedIsTrue((Long) any()))
                .thenReturn(new ArrayList<>());
        AdminServiceImpl adminService = new AdminServiceImpl(mock(AdminRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        // Act
        ResponseEntity<List<Claim>> actualAllClaimsBySupportIdAndAcceptedIsFalse = (new ClaimController(
                new ClaimServiceImpl(claimRepository, adminService,
                        new NotificationServiceImpl(notificationRepository, userRepository,
                                new SimpMessagingTemplate(mock(MessageChannel.class))))))
                .getAllClaimsBySupportIdAndAcceptedIsFalse(123L);

        // Assert
        assertTrue(actualAllClaimsBySupportIdAndAcceptedIsFalse.hasBody());
        assertEquals(200, actualAllClaimsBySupportIdAndAcceptedIsFalse.getStatusCodeValue());
        assertTrue(actualAllClaimsBySupportIdAndAcceptedIsFalse.getHeaders().isEmpty());
        verify(claimRepository).findAllBySupportIdAndAcceptedIsFalseAndTreatedIsTrue((Long) any());
    }


    /**
     * Method under test: {@link ClaimController#getAllClaimsBySupportIdAndAcceptedIsTrue(Long)}
     */
    @Test
    void testGetAllClaimsBySupportIdAndAcceptedIsTrue() {
        // Arrange
        ClaimRepository claimRepository = mock(ClaimRepository.class);
        when(claimRepository.findAllBySupportIdAndAcceptedIsTrue((Long) any())).thenReturn(new ArrayList<>());
        AdminServiceImpl adminService = new AdminServiceImpl(mock(AdminRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        // Act
        ResponseEntity<List<Claim>> actualAllClaimsBySupportIdAndAcceptedIsTrue = (new ClaimController(
                new ClaimServiceImpl(claimRepository, adminService,
                        new NotificationServiceImpl(notificationRepository, userRepository,
                                new SimpMessagingTemplate(mock(MessageChannel.class))))))
                .getAllClaimsBySupportIdAndAcceptedIsTrue(123L);

        // Assert
        assertTrue(actualAllClaimsBySupportIdAndAcceptedIsTrue.hasBody());
        assertEquals(200, actualAllClaimsBySupportIdAndAcceptedIsTrue.getStatusCodeValue());
        assertTrue(actualAllClaimsBySupportIdAndAcceptedIsTrue.getHeaders().isEmpty());
        verify(claimRepository).findAllBySupportIdAndAcceptedIsTrue((Long) any());
    }


    /**
     * Method under test: {@link ClaimController#getAllClaimsBySupportIdAndAcceptedIsNull(Long)}
     */
    @Test
    void testGetAllClaimsBySupportIdAndAcceptedIsNull() {
        // Arrange
        ClaimRepository claimRepository = mock(ClaimRepository.class);
        when(claimRepository.findAllBySupportIdAndTreatedIsFalse((Long) any())).thenReturn(new ArrayList<>());
        AdminServiceImpl adminService = new AdminServiceImpl(mock(AdminRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        // Act
        ResponseEntity<List<Claim>> actualAllClaimsBySupportIdAndAcceptedIsNull = (new ClaimController(
                new ClaimServiceImpl(claimRepository, adminService,
                        new NotificationServiceImpl(notificationRepository, userRepository,
                                new SimpMessagingTemplate(mock(MessageChannel.class))))))
                .getAllClaimsBySupportIdAndAcceptedIsNull(123L);

        // Assert
        assertTrue(actualAllClaimsBySupportIdAndAcceptedIsNull.hasBody());
        assertEquals(200, actualAllClaimsBySupportIdAndAcceptedIsNull.getStatusCodeValue());
        assertTrue(actualAllClaimsBySupportIdAndAcceptedIsNull.getHeaders().isEmpty());
        verify(claimRepository).findAllBySupportIdAndTreatedIsFalse((Long) any());
    }



    /**
     * Method under test: {@link ClaimController#createClaim(Claim)}
     */
    @Test
    void testCreateClaim2() {
        // Arrange
        AdminService adminService = mock(AdminService.class);
        when(adminService.adminWithLeastClaims()).thenReturn(null);
        ClaimRepository claimRepository = mock(ClaimRepository.class);
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        ClaimController claimController = new ClaimController(
                new ClaimServiceImpl(claimRepository, adminService, new NotificationServiceImpl(notificationRepository,
                        userRepository, new SimpMessagingTemplate(mock(MessageChannel.class)))));

        // Act
        ResponseEntity<?> actualCreateClaimResult = claimController.createClaim(new Claim());

        // Assert
        assertNull(actualCreateClaimResult.getBody());
        assertEquals(404, actualCreateClaimResult.getStatusCodeValue());
        assertTrue(actualCreateClaimResult.getHeaders().isEmpty());
        verify(adminService).adminWithLeastClaims();
    }

    /**
     * Method under test: {@link ClaimController#createClaim(Claim)}
     */
    @Test
    void testCreateClaim3() {
        // Arrange
        ClaimRepository claimRepository = mock(ClaimRepository.class);
        when(claimRepository.save((Claim) any())).thenReturn(new Claim());
        AdminService adminService = mock(AdminService.class);
        Admin admin = new Admin();
        when(adminService.adminWithLeastClaims()).thenReturn(admin);
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        when(notificationRepository.save((Notification) any())).thenReturn(new Notification());
        when(notificationRepository.findAllByUser((User) any())).thenReturn(new ArrayList<>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new Admin()));
        MessageChannel messageChannel = mock(MessageChannel.class);
        when(messageChannel.send((Message<Object>) any())).thenReturn(true);
        ClaimController claimController = new ClaimController(
                new ClaimServiceImpl(claimRepository, adminService, new NotificationServiceImpl(notificationRepository,
                        userRepository, new SimpMessagingTemplate(messageChannel))));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date postedAt = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date lastUpdatedAt = Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        Support support = new Support();

        // Act
        ResponseEntity<?> actualCreateClaimResult = claimController
                .createClaim(new Claim(123L, postedAt, lastUpdatedAt, true, true, "Hello from the Dreaming Spires",
                        "Not all who wander are lost", "Response", support, new Admin()));

        // Assert
        assertTrue(actualCreateClaimResult.hasBody());
        assertEquals(200, actualCreateClaimResult.getStatusCodeValue());
        assertTrue(actualCreateClaimResult.getHeaders().isEmpty());
        assertSame(admin, ((Claim) actualCreateClaimResult.getBody()).getAdmin());
        verify(claimRepository).save((Claim) any());
        verify(adminService).adminWithLeastClaims();
        verify(notificationRepository).save((Notification) any());
        verify(notificationRepository).findAllByUser((User) any());
        verify(userRepository, atLeast(1)).findById((Long) any());
        verify(messageChannel).send((Message<Object>) any());
    }

    /**
     * Method under test: {@link ClaimController#deleteClaim(Long)}
     */
    @Test
    void testDeleteClaim() {
        // Arrange
        ClaimRepository claimRepository = mock(ClaimRepository.class);
        doNothing().when(claimRepository).deleteById((Long) any());
        AdminServiceImpl adminService = new AdminServiceImpl(mock(AdminRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        // Act
        ResponseEntity<?> actualDeleteClaimResult = (new ClaimController(
                new ClaimServiceImpl(claimRepository, adminService, new NotificationServiceImpl(notificationRepository,
                        userRepository, new SimpMessagingTemplate(mock(MessageChannel.class)))))).deleteClaim(123L);

        // Assert
        assertEquals("Claim Deleted", actualDeleteClaimResult.getBody());
        assertEquals(200, actualDeleteClaimResult.getStatusCodeValue());
        assertTrue(actualDeleteClaimResult.getHeaders().isEmpty());
        verify(claimRepository).deleteById((Long) any());
    }


    /**
     * Method under test: {@link ClaimController#updateClaimSituation(Long, boolean)}
     */
    @Test
    void testUpdateClaimSituation() {
        // Arrange
        ClaimRepository claimRepository = mock(ClaimRepository.class);
        when(claimRepository.save((Claim) any())).thenReturn(new Claim());
        when(claimRepository.findById((Long) any())).thenReturn(Optional.of(new Claim()));
        AdminServiceImpl adminService = new AdminServiceImpl(mock(AdminRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        // Act
        ResponseEntity<?> actualUpdateClaimSituationResult = (new ClaimController(
                new ClaimServiceImpl(claimRepository, adminService, new NotificationServiceImpl(notificationRepository,
                        userRepository, new SimpMessagingTemplate(mock(MessageChannel.class)))))).updateClaimSituation(123L,
                true);

        // Assert
        assertTrue(actualUpdateClaimSituationResult.hasBody());
        assertEquals(200, actualUpdateClaimSituationResult.getStatusCodeValue());
        assertTrue(actualUpdateClaimSituationResult.getHeaders().isEmpty());
        verify(claimRepository).save((Claim) any());
        verify(claimRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ClaimController#updateClaimSituation(Long, boolean)}
     */
    @Test
    void testUpdateClaimSituation2() {
        // Arrange
        ClaimRepository claimRepository = mock(ClaimRepository.class);
        when(claimRepository.save((Claim) any())).thenReturn(null);
        when(claimRepository.findById((Long) any())).thenReturn(Optional.of(new Claim()));
        AdminServiceImpl adminService = new AdminServiceImpl(mock(AdminRepository.class));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        // Act
        ResponseEntity<?> actualUpdateClaimSituationResult = (new ClaimController(
                new ClaimServiceImpl(claimRepository, adminService, new NotificationServiceImpl(notificationRepository,
                        userRepository, new SimpMessagingTemplate(mock(MessageChannel.class)))))).updateClaimSituation(123L,
                true);

        // Assert
        assertNull(actualUpdateClaimSituationResult.getBody());
        assertEquals(404, actualUpdateClaimSituationResult.getStatusCodeValue());
        assertTrue(actualUpdateClaimSituationResult.getHeaders().isEmpty());
        verify(claimRepository).save((Claim) any());
        verify(claimRepository).findById((Long) any());
    }


    /**
     * Method under test: {@link ClaimController#addResponseClaim(Claim)}
     */
    @Test
    void testAddResponseClaim2() {
        // Arrange
        ClaimRepository claimRepository = mock(ClaimRepository.class);
        when(claimRepository.save((Claim) any())).thenReturn(new Claim());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date postedAt = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date lastUpdatedAt = Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        Support support = new Support();
        when(claimRepository.findById((Long) any())).thenReturn(Optional.of(new Claim(123L, postedAt, lastUpdatedAt, true,
                true, "Hello from wars", "Not all who wander are lost", "Response", support, new Admin())));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        when(notificationRepository.save((Notification) any())).thenReturn(new Notification());
        when(notificationRepository.findAllByUser((User) any())).thenReturn(new ArrayList<>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new Admin()));
        MessageChannel messageChannel = mock(MessageChannel.class);
        when(messageChannel.send((Message<Object>) any())).thenReturn(true);
        NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationRepository, userRepository,
                new SimpMessagingTemplate(messageChannel));

        ClaimController claimController = new ClaimController(new ClaimServiceImpl(claimRepository,
                new AdminServiceImpl(mock(AdminRepository.class)), notificationService));

        // Act
        ResponseEntity<?> actualAddResponseClaimResult = claimController.addResponseClaim(new Claim());

        // Assert
        assertTrue(actualAddResponseClaimResult.hasBody());
        assertEquals(200, actualAddResponseClaimResult.getStatusCodeValue());
        assertTrue(actualAddResponseClaimResult.getHeaders().isEmpty());
        verify(claimRepository).save((Claim) any());
        verify(claimRepository).findById((Long) any());
        verify(notificationRepository).save((Notification) any());
        verify(notificationRepository).findAllByUser((User) any());
        verify(userRepository, atLeast(1)).findById((Long) any());
        verify(messageChannel).send((Message<Object>) any());
    }

    /**
     * Method under test: {@link ClaimController#addResponseClaim(Claim)}
     */
    @Test
    void testAddResponseClaim3() {
        // Arrange
        ClaimRepository claimRepository = mock(ClaimRepository.class);
        when(claimRepository.save((Claim) any())).thenReturn(null);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date postedAt = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date lastUpdatedAt = Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        Support support = new Support();
        when(claimRepository.findById((Long) any())).thenReturn(Optional.of(new Claim(123L, postedAt, lastUpdatedAt, true,
                true, "Hello from the Dreaming Spires", "Not all who wander are lost", "Response", support, new Admin())));
        NotificationRepository notificationRepository = mock(NotificationRepository.class);
        when(notificationRepository.save((Notification) any())).thenReturn(new Notification());
        when(notificationRepository.findAllByUser((User) any())).thenReturn(new ArrayList<>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new Admin()));
        MessageChannel messageChannel = mock(MessageChannel.class);
        when(messageChannel.send((Message<Object>) any())).thenReturn(true);
        NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationRepository, userRepository,
                new SimpMessagingTemplate(messageChannel));

        ClaimController claimController = new ClaimController(new ClaimServiceImpl(claimRepository,
                new AdminServiceImpl(mock(AdminRepository.class)), notificationService));

        // Act
        ResponseEntity<?> actualAddResponseClaimResult = claimController.addResponseClaim(new Claim());

        // Assert
        assertNull(actualAddResponseClaimResult.getBody());
        assertEquals(404, actualAddResponseClaimResult.getStatusCodeValue());
        assertTrue(actualAddResponseClaimResult.getHeaders().isEmpty());
        verify(claimRepository).save((Claim) any());
        verify(claimRepository).findById((Long) any());
        verify(notificationRepository).save((Notification) any());
        verify(notificationRepository).findAllByUser((User) any());
        verify(userRepository, atLeast(1)).findById((Long) any());
        verify(messageChannel).send((Message<Object>) any());
    }
}

