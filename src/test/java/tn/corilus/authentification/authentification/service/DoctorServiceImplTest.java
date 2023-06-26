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
import tn.corilus.authentification.authentification.entities.Doctor;
import tn.corilus.authentification.authentification.repository.DoctorRepository;

@ContextConfiguration(classes = {DoctorServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DoctorServiceImplTest {
    @MockBean
    private AdminService adminService;

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorServiceImpl doctorServiceImpl;

    /**
     * Method under test: {@link DoctorServiceImpl#getDoctor(Long)}
     */
    @Test
    void testGetDoctor() {
        // Arrange
        Doctor doctor = new Doctor();
        when(doctorRepository.findById((Long) any())).thenReturn(Optional.of(doctor));

        // Act and Assert
        assertSame(doctor, doctorServiceImpl.getDoctor(123L));
        verify(doctorRepository).findById((Long) any());
    }


    /**
     * Method under test: {@link DoctorServiceImpl#addPicture(Long, MultipartFile)}
     */
    @Test
    void testAddPicture3() throws IOException {
        // Arrange
        when(doctorRepository.findById((Long) any())).thenReturn(Optional.empty());

        // Act and Assert
        assertEquals("Doctor not found", doctorServiceImpl.addPicture(1L,
                new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(doctorRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link DoctorServiceImpl#modifyDoctor(Doctor)}
     */
    @Test
    void testModifyDoctor() {
        // Arrange
        Doctor doctor = new Doctor();
        when(doctorRepository.save((Doctor) any())).thenReturn(doctor);
        when(doctorRepository.findById((Long) any())).thenReturn(Optional.of(new Doctor()));

        // Act and Assert
        assertSame(doctor, doctorServiceImpl.modifyDoctor(new Doctor()));
        verify(doctorRepository).save((Doctor) any());
        verify(doctorRepository).findById((Long) any());
    }



    /**
     * Method under test: {@link DoctorServiceImpl#getAllDoctorsOrderdByApprooved()}
     */
    @Test
    void testGetAllDoctorsOrderdByApprooved() {
        // Arrange
        ArrayList<Doctor> doctorList = new ArrayList<>();
        when(doctorRepository.getAllDoctorsOrderdByApprooved()).thenReturn(doctorList);

        // Act
        List<Doctor> actualAllDoctorsOrderdByApprooved = doctorServiceImpl.getAllDoctorsOrderdByApprooved();

        // Assert
        assertSame(doctorList, actualAllDoctorsOrderdByApprooved);
        assertTrue(actualAllDoctorsOrderdByApprooved.isEmpty());
        verify(doctorRepository).getAllDoctorsOrderdByApprooved();
    }

    /**
     * Method under test: {@link DoctorServiceImpl#approveDoctor(Long, Long)}
     */
    @Test
    void testApproveDoctor() {
        // Arrange
        Doctor doctor = new Doctor();
        when(doctorRepository.save((Doctor) any())).thenReturn(doctor);
        when(doctorRepository.findById((Long) any())).thenReturn(Optional.of(new Doctor()));
        when(adminService.getAdmin((Long) any())).thenReturn(new Admin());

        // Act and Assert
        assertSame(doctor, doctorServiceImpl.approveDoctor(123L, 1L));
        verify(doctorRepository).save((Doctor) any());
        verify(doctorRepository).findById((Long) any());
        verify(adminService).getAdmin((Long) any());
    }


    /**
     * Method under test: {@link DoctorServiceImpl#blockDoctor(Long, Long)}
     */
    @Test
    void testBlockDoctor() {
        // Arrange
        Doctor doctor = new Doctor();
        when(doctorRepository.save((Doctor) any())).thenReturn(doctor);
        when(doctorRepository.findById((Long) any())).thenReturn(Optional.of(new Doctor()));
        when(adminService.getAdmin((Long) any())).thenReturn(new Admin());

        // Act and Assert
        assertSame(doctor, doctorServiceImpl.blockDoctor(123L, 1L));
        verify(doctorRepository).save((Doctor) any());
        verify(doctorRepository).findById((Long) any());
        verify(adminService).getAdmin((Long) any());
    }


    /**
     * Method under test: {@link DoctorServiceImpl#unblockDoctor(Long, Long)}
     */
    @Test
    void testUnblockDoctor() {
        // Arrange
        Doctor doctor = new Doctor();
        when(doctorRepository.save((Doctor) any())).thenReturn(doctor);
        when(doctorRepository.findById((Long) any())).thenReturn(Optional.of(new Doctor()));
        when(adminService.getAdmin((Long) any())).thenReturn(new Admin());

        // Act and Assert
        assertSame(doctor, doctorServiceImpl.unblockDoctor(123L, 1L));
        verify(doctorRepository).save((Doctor) any());
        verify(doctorRepository).findById((Long) any());
        verify(adminService).getAdmin((Long) any());
    }


    /**
     * Method under test: {@link DoctorServiceImpl#getDoctorsAddedThisMonth()}
     */
    @Test
    void testGetDoctorsAddedThisMonth() {
        // Arrange
        when(doctorRepository.findDoctorsAddedThisMonth()).thenReturn(new ArrayList<>());

        // Act and Assert
        assertEquals("0", doctorServiceImpl.getDoctorsAddedThisMonth());
        verify(doctorRepository).findDoctorsAddedThisMonth();
    }
}

