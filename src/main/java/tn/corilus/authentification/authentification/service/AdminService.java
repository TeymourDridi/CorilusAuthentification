package tn.corilus.authentification.authentification.service;

import org.springframework.web.multipart.MultipartFile;
import tn.corilus.authentification.authentification.entities.Admin;

import java.io.IOException;
import java.util.List;

/**
 * Admin CRUD
 *
 * @author Teymour Dridi
 */
public interface AdminService {

    Admin getAdmin(Long id);

    String addPicture(Long idUser, MultipartFile file) throws IOException;

    Admin modifyAdmin(Admin admin);

    List<Admin> getAllAdmins();
    Admin adminWithLeastClaims();
}
