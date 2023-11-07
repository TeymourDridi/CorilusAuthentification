package tn.corilus.authentification.authentification.service;

import org.springframework.web.multipart.MultipartFile;
import tn.corilus.authentification.authentification.entities.Doctor;

import java.io.IOException;
import java.util.List;

/**
 * Doctor CRUD
 *
 * @author Teymour Dridi
 */
public interface DoctorService {

    Doctor getDoctor(Long id);
    String addPicture(Long idUser, MultipartFile file) throws IOException;
    Doctor modifyDoctor(Doctor doctor);
    String deleteDoctor(Long id);
    List<Doctor> getAllDoctorsOrderdByApprooved();
    Doctor approveDoctor(Long id,Long idAdmin);
    Doctor blockDoctor(Long id,Long idAdmin);
    Doctor unblockDoctor(Long id,Long idAdmin);
    String getDoctorsAddedThisMonth();
}
