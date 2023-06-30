package tn.corilus.authentification.authentification.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.entities.Doctor;
import tn.corilus.authentification.authentification.repository.AdminRepository;
import tn.corilus.authentification.authentification.repository.DoctorRepository;
import tn.corilus.authentification.authentification.service.AdminServiceImpl;
import tn.corilus.authentification.authentification.service.DoctorServiceImpl;

class DoctorControllerTest {
    /**
     * Method under test: {@link DoctorController#approoveDoctor(Long, Long)}
     */
    @Test
    void testApprooveDoctor() {

        // Arrange
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        when(doctorRepository.save((Doctor) any())).thenReturn(new Doctor());
        when(doctorRepository.findById((Long) any())).thenReturn(Optional.of(new Doctor()));
        AdminRepository adminRepository = mock(AdminRepository.class);
        when(adminRepository.findById((Long) any())).thenReturn(Optional.of(new Admin()));

        // Act
        ResponseEntity<Doctor> actualApprooveDoctorResult = (new DoctorController(
                new DoctorServiceImpl(doctorRepository, new AdminServiceImpl(adminRepository)))).approoveDoctor(1L, 1L);

        // Assert
        assertTrue(actualApprooveDoctorResult.hasBody());
        assertTrue(actualApprooveDoctorResult.getHeaders().isEmpty());
        assertEquals(200, actualApprooveDoctorResult.getStatusCodeValue());
        verify(doctorRepository).save((Doctor) any());
        verify(doctorRepository).findById((Long) any());
        verify(adminRepository).findById((Long) any());
    }


}

