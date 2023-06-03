package tn.corilus.authentification.authentification.service;

import org.springframework.web.multipart.MultipartFile;
import tn.corilus.authentification.authentification.entities.Doctor;

import javax.print.Doc;
import java.io.IOException;

/**
 * Doctor CRUD
 *
 * @author Teymour Dridi
 */
public interface DoctorService {

    Doctor getDoctor(Long id);
    String addPicture(Long idUser, MultipartFile file) throws IOException;
    Doctor modifyDoctor(Doctor doctor);
}
