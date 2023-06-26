package tn.corilus.authentification.authentification.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.entities.Support;
import tn.corilus.authentification.authentification.entities.SupportSignUpRequest;
import tn.corilus.authentification.authentification.repository.RoleRepository;
import tn.corilus.authentification.authentification.repository.UserRepository;

@ContextConfiguration(classes = {AuthServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AuthServiceImplTest {
    @MockBean
    private AdminService adminService;

    @Autowired
    private AuthServiceImpl authServiceImpl;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link AuthServiceImpl#usernameOrMailAlreadyExists(SupportSignUpRequest)}
     */
    @Test
    void testUsernameOrMailAlreadyExists() {
        // Arrange
        when(userRepository.existsByEmail((String) any())).thenReturn(true);
        when(userRepository.existsByUsername((String) any())).thenReturn(true);

        // Act and Assert
        assertEquals("Error: Username is already taken!",
                authServiceImpl.usernameOrMailAlreadyExists(new SupportSignUpRequest()));
        verify(userRepository).existsByUsername((String) any());
    }

    /**
     * Method under test: {@link AuthServiceImpl#usernameOrMailAlreadyExists(SupportSignUpRequest)}
     */
    @Test
    void testUsernameOrMailAlreadyExists2() {
        // Arrange
        when(userRepository.existsByEmail((String) any())).thenReturn(true);
        when(userRepository.existsByUsername((String) any())).thenReturn(false);

        // Act and Assert
        assertEquals("Error: Email is already in use!",
                authServiceImpl.usernameOrMailAlreadyExists(new SupportSignUpRequest()));
        verify(userRepository).existsByEmail((String) any());
        verify(userRepository).existsByUsername((String) any());
    }


    /**
     * Method under test: {@link AuthServiceImpl#usernameOrMailAlreadyExists(SupportSignUpRequest)}
     */
    @Test
    void testUsernameOrMailAlreadyExists4() {
        // Arrange
        when(userRepository.existsByEmail((String) any())).thenThrow(new RuntimeException());
        when(userRepository.existsByUsername((String) any())).thenThrow(new RuntimeException());

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> authServiceImpl.usernameOrMailAlreadyExists(new SupportSignUpRequest()));
        verify(userRepository).existsByUsername((String) any());
    }

    /**
     * Method under test: {@link AuthServiceImpl#usernameOrMailAlreadyExists(SupportSignUpRequest)}
     */
    @Test
    void testUsernameOrMailAlreadyExists5() {
        // Arrange
        when(userRepository.existsByEmail((String) any())).thenReturn(false);
        when(userRepository.existsByUsername((String) any())).thenReturn(false);

        // Act and Assert
        assertEquals("Valid", authServiceImpl.usernameOrMailAlreadyExists(new SupportSignUpRequest()));
        verify(userRepository).existsByEmail((String) any());
        verify(userRepository).existsByUsername((String) any());
    }

    /**
     * Method under test: {@link AuthServiceImpl#registerSupport(SupportSignUpRequest, Long)}
     */
    @Test
    void testRegisterSupport() {
        // Arrange
        when(adminService.getAdmin((Long) any())).thenReturn(new Admin());
        Support support = new Support();
        when(userRepository.save((Support) any())).thenReturn(support);
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        // Act and Assert
        assertSame(support, authServiceImpl.registerSupport(new SupportSignUpRequest(), 1L));
        verify(adminService).getAdmin((Long) any());
        verify(userRepository).save((Support) any());
        verify(passwordEncoder).encode((CharSequence) any());
    }




}

