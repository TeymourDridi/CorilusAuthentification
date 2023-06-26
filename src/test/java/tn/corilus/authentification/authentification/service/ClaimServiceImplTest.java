package tn.corilus.authentification.authentification.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.entities.Claim;
import tn.corilus.authentification.authentification.entities.Notification;
import tn.corilus.authentification.authentification.entities.Support;
import tn.corilus.authentification.authentification.repository.ClaimRepository;

@ContextConfiguration(classes = {ClaimServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ClaimServiceImplTest {
    @MockBean
    private AdminService adminService;

    @MockBean
    private ClaimRepository claimRepository;

    @Autowired
    private ClaimServiceImpl claimServiceImpl;

    @MockBean
    private NotificationService notificationService;

    /**
     * Method under test: {@link ClaimServiceImpl#getAllClaimsByAdminIdAndAcceptedIsNull(Long)}
     */
    @Test
    void testGetAllClaimsByAdminIdAndAcceptedIsNull() {
        // Arrange
        ArrayList<Claim> claimList = new ArrayList<>();
        when(claimRepository.findAllByAdminIdAndTreatedIsFalse((Long) any())).thenReturn(claimList);

        // Act
        List<Claim> actualAllClaimsByAdminIdAndAcceptedIsNull = claimServiceImpl
                .getAllClaimsByAdminIdAndAcceptedIsNull(123L);

        // Assert
        assertSame(claimList, actualAllClaimsByAdminIdAndAcceptedIsNull);
        assertTrue(actualAllClaimsByAdminIdAndAcceptedIsNull.isEmpty());
        verify(claimRepository).findAllByAdminIdAndTreatedIsFalse((Long) any());
    }

    /**
     * Method under test: {@link ClaimServiceImpl#getAllClaimsByAdminIdAndAcceptedIsFalse(Long)}
     */
    @Test
    void testGetAllClaimsByAdminIdAndAcceptedIsFalse() {
        // Arrange
        ArrayList<Claim> claimList = new ArrayList<>();
        when(claimRepository.findAllByAdminIdAndAcceptedIsFalseAndTreatedIsTrue((Long) any())).thenReturn(claimList);

        // Act
        List<Claim> actualAllClaimsByAdminIdAndAcceptedIsFalse = claimServiceImpl
                .getAllClaimsByAdminIdAndAcceptedIsFalse(123L);

        // Assert
        assertSame(claimList, actualAllClaimsByAdminIdAndAcceptedIsFalse);
        assertTrue(actualAllClaimsByAdminIdAndAcceptedIsFalse.isEmpty());
        verify(claimRepository).findAllByAdminIdAndAcceptedIsFalseAndTreatedIsTrue((Long) any());
    }

    /**
     * Method under test: {@link ClaimServiceImpl#getAllClaimsByAdminIdAndAcceptedIsTrue(Long)}
     */
    @Test
    void testGetAllClaimsByAdminIdAndAcceptedIsTrue() {
        // Arrange
        ArrayList<Claim> claimList = new ArrayList<>();
        when(claimRepository.findAllByAdminIdAndAcceptedIsTrue((Long) any())).thenReturn(claimList);

        // Act
        List<Claim> actualAllClaimsByAdminIdAndAcceptedIsTrue = claimServiceImpl
                .getAllClaimsByAdminIdAndAcceptedIsTrue(123L);

        // Assert
        assertSame(claimList, actualAllClaimsByAdminIdAndAcceptedIsTrue);
        assertTrue(actualAllClaimsByAdminIdAndAcceptedIsTrue.isEmpty());
        verify(claimRepository).findAllByAdminIdAndAcceptedIsTrue((Long) any());
    }

    /**
     * Method under test: {@link ClaimServiceImpl#getAllClaimsBySupportIdAndAcceptedIsNull(Long)}
     */
    @Test
    void testGetAllClaimsBySupportIdAndAcceptedIsNull() {
        // Arrange
        ArrayList<Claim> claimList = new ArrayList<>();
        when(claimRepository.findAllBySupportIdAndTreatedIsFalse((Long) any())).thenReturn(claimList);

        // Act
        List<Claim> actualAllClaimsBySupportIdAndAcceptedIsNull = claimServiceImpl
                .getAllClaimsBySupportIdAndAcceptedIsNull(123L);

        // Assert
        assertSame(claimList, actualAllClaimsBySupportIdAndAcceptedIsNull);
        assertTrue(actualAllClaimsBySupportIdAndAcceptedIsNull.isEmpty());
        verify(claimRepository).findAllBySupportIdAndTreatedIsFalse((Long) any());
    }

    /**
     * Method under test: {@link ClaimServiceImpl#getAllClaimsSupportIdAndAcceptedIsFalse(Long)}
     */
    @Test
    void testGetAllClaimsSupportIdAndAcceptedIsFalse() {
        // Arrange
        ArrayList<Claim> claimList = new ArrayList<>();
        when(claimRepository.findAllBySupportIdAndAcceptedIsFalseAndTreatedIsTrue((Long) any())).thenReturn(claimList);

        // Act
        List<Claim> actualAllClaimsSupportIdAndAcceptedIsFalse = claimServiceImpl
                .getAllClaimsSupportIdAndAcceptedIsFalse(123L);

        // Assert
        assertSame(claimList, actualAllClaimsSupportIdAndAcceptedIsFalse);
        assertTrue(actualAllClaimsSupportIdAndAcceptedIsFalse.isEmpty());
        verify(claimRepository).findAllBySupportIdAndAcceptedIsFalseAndTreatedIsTrue((Long) any());
    }

    /**
     * Method under test: {@link ClaimServiceImpl#getAllClaimsSupportIdAndAcceptedIsTrue(Long)}
     */
    @Test
    void testGetAllClaimsSupportIdAndAcceptedIsTrue() {
        // Arrange
        ArrayList<Claim> claimList = new ArrayList<>();
        when(claimRepository.findAllBySupportIdAndAcceptedIsTrue((Long) any())).thenReturn(claimList);

        // Act
        List<Claim> actualAllClaimsSupportIdAndAcceptedIsTrue = claimServiceImpl
                .getAllClaimsSupportIdAndAcceptedIsTrue(123L);

        // Assert
        assertSame(claimList, actualAllClaimsSupportIdAndAcceptedIsTrue);
        assertTrue(actualAllClaimsSupportIdAndAcceptedIsTrue.isEmpty());
        verify(claimRepository).findAllBySupportIdAndAcceptedIsTrue((Long) any());
    }



    /**
     * Method under test: {@link ClaimServiceImpl#createClaim(Claim)}
     */
    @Test
    void testCreateClaim2() {
        // Arrange
        when(adminService.adminWithLeastClaims()).thenReturn(null);

        // Act and Assert
        assertNull(claimServiceImpl.createClaim(new Claim()));
        verify(adminService).adminWithLeastClaims();
    }

    /**
     * Method under test: {@link ClaimServiceImpl#createClaim(Claim)}
     */
    @Test
    void testCreateClaim3() {
        // Arrange
        Claim claim = new Claim();
        when(claimRepository.save((Claim) any())).thenReturn(claim);
        Admin admin = new Admin();
        when(adminService.adminWithLeastClaims()).thenReturn(admin);
        when(notificationService.addNotification((String) any(), (Long) any())).thenReturn(new Notification());

        Claim claim1 = new Claim();
        claim1.setSupport(new Support());

        // Act and Assert
        assertSame(claim, claimServiceImpl.createClaim(claim1));
        verify(claimRepository).save((Claim) any());
        verify(adminService).adminWithLeastClaims();
        verify(notificationService).addNotification((String) any(), (Long) any());
        assertSame(admin, claim1.getAdmin());
    }

    /**
     * Method under test: {@link ClaimServiceImpl#deleteClaim(Long)}
     */
    @Test
    void testDeleteClaim() {
        // Arrange
        doNothing().when(claimRepository).deleteById((Long) any());

        // Act and Assert
        assertEquals("Claim Deleted", claimServiceImpl.deleteClaim(123L));
        verify(claimRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link ClaimServiceImpl#updateSituation(Long, boolean)}
     */
    @Test
    void testUpdateSituation() {
        // Arrange
        Claim claim = new Claim();
        when(claimRepository.save((Claim) any())).thenReturn(claim);
        when(claimRepository.findById((Long) any())).thenReturn(Optional.of(new Claim()));

        // Act and Assert
        assertSame(claim, claimServiceImpl.updateSituation(123L, true));
        verify(claimRepository).save((Claim) any());
        verify(claimRepository).findById((Long) any());
    }



    /**
     * Method under test: {@link ClaimServiceImpl#updateSituation(Long, boolean)}
     */
    @Test
    void testUpdateSituation3() {
        // Arrange
        when(claimRepository.save((Claim) any())).thenReturn(new Claim());
        when(claimRepository.findById((Long) any())).thenReturn(Optional.empty());

        // Act and Assert
        assertNull(claimServiceImpl.updateSituation(123L, true));
        verify(claimRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ClaimServiceImpl#addResponse(Claim)}
     */
    @Test
    void testAddResponse3() {
        // Arrange
        when(claimRepository.findById((Long) any())).thenReturn(Optional.empty());

        // Act and Assert
        assertNull(claimServiceImpl.addResponse(new Claim()));
        verify(claimRepository).findById((Long) any());
    }


    /**
     * Method under test: {@link ClaimServiceImpl#addResponse(Claim)}
     */
    @Test
    void testAddResponse6() {
        // Arrange
        Claim claim = mock(Claim.class);
        when(claim.getSupport()).thenReturn(new Support());
        when(claim.getSubject()).thenReturn("Hello from the Dreaming Spires");
        when(claim.getAdmin()).thenReturn(new Admin());
        doNothing().when(claim).setAccepted(anyBoolean());
        doNothing().when(claim).setAdmin((Admin) any());
        doNothing().when(claim).setResponse((String) any());
        doNothing().when(claim).setTreated(anyBoolean());
        claim.setAdmin(null);
        Optional<Claim> ofResult = Optional.of(claim);
        Claim claim1 = new Claim();
        when(claimRepository.save((Claim) any())).thenReturn(claim1);
        when(claimRepository.findById((Long) any())).thenReturn(ofResult);
        when(notificationService.addNotification((String) any(), (Long) any())).thenReturn(new Notification());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date postedAt = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date lastUpdatedAt = Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        Support support = new Support();

        // Act and Assert
        assertSame(claim1, claimServiceImpl.addResponse(new Claim(123L, postedAt, lastUpdatedAt, true, true,
                "Hello from the Dreaming Spires", "Not all who wander are lost", "Response", support, new Admin())));
        verify(claimRepository).save((Claim) any());
        verify(claimRepository).findById((Long) any());
        verify(claim).getSubject();
        verify(claim, atLeast(1)).getAdmin();
        verify(claim).getSupport();
        verify(claim).setAccepted(anyBoolean());
        verify(claim).setAdmin((Admin) any());
        verify(claim).setResponse((String) any());
        verify(claim).setTreated(anyBoolean());
        verify(notificationService).addNotification((String) any(), (Long) any());
    }


    /**
     * Method under test: {@link ClaimServiceImpl#addResponse(Claim)}
     */
    @Test
    void testAddResponse9() {
        // Arrange
        Claim claim = mock(Claim.class);
        when(claim.getSupport()).thenReturn(new Support());
        when(claim.getSubject()).thenReturn("Hello from the Dreaming Spires");
        when(claim.getAdmin()).thenReturn(new Admin());
        doNothing().when(claim).setAccepted(anyBoolean());
        doNothing().when(claim).setAdmin((Admin) any());
        doNothing().when(claim).setResponse((String) any());
        doNothing().when(claim).setTreated(anyBoolean());
        claim.setAdmin(null);
        Optional<Claim> ofResult = Optional.of(claim);
        Claim claim1 = new Claim();
        when(claimRepository.save((Claim) any())).thenReturn(claim1);
        when(claimRepository.findById((Long) any())).thenReturn(ofResult);
        when(notificationService.addNotification((String) any(), (Long) any())).thenReturn(new Notification());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date postedAt = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date lastUpdatedAt = Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        Support support = new Support();

        // Act and Assert
        assertSame(claim1, claimServiceImpl.addResponse(new Claim(123L, postedAt, lastUpdatedAt, false, true,
                "Hello from the Dreaming Spires", "Not all who wander are lost", "Response", support, new Admin())));
        verify(claimRepository).save((Claim) any());
        verify(claimRepository).findById((Long) any());
        verify(claim).getSubject();
        verify(claim, atLeast(1)).getAdmin();
        verify(claim).getSupport();
        verify(claim).setAccepted(anyBoolean());
        verify(claim).setAdmin((Admin) any());
        verify(claim).setResponse((String) any());
        verify(claim).setTreated(anyBoolean());
        verify(notificationService).addNotification((String) any(), (Long) any());
    }
}

