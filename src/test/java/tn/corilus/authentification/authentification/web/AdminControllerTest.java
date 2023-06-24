package tn.corilus.authentification.authentification.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.repository.AdminRepository;
import tn.corilus.authentification.authentification.service.AdminServiceImpl;

class AdminControllerTest {
    /**
     * Method under test: {@link AdminController#getAdmin()}
     */
    @Test
    void testGetAdmin() {

        // Arrange
        AdminRepository adminRepository = mock(AdminRepository.class);
        when(adminRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<List<Admin>> actualAdmin = (new AdminController(new AdminServiceImpl(adminRepository))).getAdmin();

        // Assert
        assertTrue(actualAdmin.hasBody());
        assertEquals(200, actualAdmin.getStatusCodeValue());
        assertTrue(actualAdmin.getHeaders().isEmpty());
        verify(adminRepository).findAll();
    }


    /**
     * Method under test: {@link AdminController#getAdmin(Long)}
     */
    @Test
    void testGetAdmin3() {
        // Arrange
        AdminRepository adminRepository = mock(AdminRepository.class);
        when(adminRepository.findById((Long) any())).thenReturn(Optional.of(new Admin()));

        // Act
        ResponseEntity<Admin> actualAdmin = (new AdminController(new AdminServiceImpl(adminRepository))).getAdmin(123L);

        // Assert
        assertTrue(actualAdmin.hasBody());
        assertTrue(actualAdmin.getHeaders().isEmpty());
        assertEquals(200, actualAdmin.getStatusCodeValue());
        verify(adminRepository).findById((Long) any());
    }


    /**
     * Method under test: {@link AdminController#getAdmin(Admin)}
     */
    @Test
    void testGetAdmin5() {

        // Arrange
        AdminRepository adminRepository = mock(AdminRepository.class);
        when(adminRepository.save((Admin) any())).thenReturn(new Admin());
        when(adminRepository.findById((Long) any())).thenReturn(Optional.of(new Admin()));
        AdminController adminController = new AdminController(new AdminServiceImpl(adminRepository));

        // Act
        ResponseEntity<Admin> actualAdmin = adminController.getAdmin(new Admin());

        // Assert
        assertTrue(actualAdmin.hasBody());
        assertTrue(actualAdmin.getHeaders().isEmpty());
        assertEquals(200, actualAdmin.getStatusCodeValue());
        verify(adminRepository).save((Admin) any());
        verify(adminRepository).findById((Long) any());
    }


    /**
     * Method under test: {@link AdminController#getImageWithMediaType(String)}
     */
    @Test
    void testGetImageWithMediaType() throws IOException {

        // Arrange and Act
        ResponseEntity<byte[]> actualImageWithMediaType = (new AdminController(
                new AdminServiceImpl(mock(AdminRepository.class)))).getImageWithMediaType("Picture Name");

        // Assert
        assertNull(actualImageWithMediaType.getBody());
        assertEquals(404, actualImageWithMediaType.getStatusCodeValue());
        assertTrue(actualImageWithMediaType.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link AdminController#getImageWithMediaType(String)}
     */
    @Test
    void testGetImageWithMediaType2() throws IOException {

        // Arrange and Act
        ResponseEntity<byte[]> actualImageWithMediaType = (new AdminController(
                new AdminServiceImpl(mock(AdminRepository.class)))).getImageWithMediaType("");

        // Assert
        assertNull(actualImageWithMediaType.getBody());
        assertEquals(404, actualImageWithMediaType.getStatusCodeValue());
        assertTrue(actualImageWithMediaType.getHeaders().isEmpty());
    }
}

