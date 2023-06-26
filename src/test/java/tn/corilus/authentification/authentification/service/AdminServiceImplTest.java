package tn.corilus.authentification.authentification.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.repository.AdminRepository;

@ContextConfiguration(classes = {AdminServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AdminServiceImplTest {
    @MockBean
    private AdminRepository adminRepository;

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    /**
     * Method under test: {@link AdminServiceImpl#getAdmin(Long)}
     */
    @Test
    void testGetAdmin() {
        // Arrange
        Admin admin = new Admin();
        when(adminRepository.findById((Long) any())).thenReturn(Optional.of(admin));

        // Act and Assert
        assertSame(admin, adminServiceImpl.getAdmin(123L));
        verify(adminRepository).findById((Long) any());
    }


    /**
     * Method under test: {@link AdminServiceImpl#addPicture(Long, MultipartFile)}
     */
    @Test
    void testAddPicture3() throws IOException {
        // Arrange
        when(adminRepository.findById((Long) any())).thenReturn(Optional.empty());

        // Act and Assert
        assertEquals("Doctor not found", adminServiceImpl.addPicture(1L,
                new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(adminRepository).findById((Long) any());
    }


    /**
     * Method under test: {@link AdminServiceImpl#modifyAdmin(Admin)}
     */
    @Test
    void testModifyAdmin() {
        // Arrange
        Admin admin = new Admin();
        when(adminRepository.save((Admin) any())).thenReturn(admin);
        when(adminRepository.findById((Long) any())).thenReturn(Optional.of(new Admin()));

        // Act and Assert
        assertSame(admin, adminServiceImpl.modifyAdmin(new Admin()));
        verify(adminRepository).save((Admin) any());
        verify(adminRepository).findById((Long) any());
    }



    /**
     * Method under test: {@link AdminServiceImpl#getAllAdmins()}
     */
    @Test
    void testGetAllAdmins() {
        // Arrange
        ArrayList<Admin> adminList = new ArrayList<>();
        when(adminRepository.findAll()).thenReturn(adminList);

        // Act
        List<Admin> actualAllAdmins = adminServiceImpl.getAllAdmins();

        // Assert
        assertSame(adminList, actualAllAdmins);
        assertTrue(actualAllAdmins.isEmpty());
        verify(adminRepository).findAll();
    }


    /**
     * Method under test: {@link AdminServiceImpl#adminWithLeastClaims()}
     */
    @Test
    void testAdminWithLeastClaims2() {
        // Arrange
        ArrayList<Admin> adminList = new ArrayList<>();
        Admin admin = new Admin();
        adminList.add(admin);
        when(adminRepository.findAdminWithLeastClaims()).thenReturn(adminList);

        // Act and Assert
        assertSame(admin, adminServiceImpl.adminWithLeastClaims());
        verify(adminRepository).findAdminWithLeastClaims();
    }
}

