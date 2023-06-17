package tn.corilus.authentification.authentification.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.repository.AdminRepository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    @Override
    public Admin getAdmin(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public String addPicture(Long idUser, MultipartFile file) throws IOException {

        Admin admin = adminRepository.findById(idUser).orElse(null);
        if (admin == null) {
            return "Doctor not found";
        }
        String fileName=admin.getUsername()+"."+file.getContentType().replaceAll("image/","");
        Path uploadPath = Paths.get("pictures", fileName);
        File uploadDir = new File(uploadPath.toString()).getParentFile();
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        if (!file.isEmpty()) {
            try {
                // Define the file path where you want to save the picture

                System.out.println(uploadPath);
                // Save the file to the specified location
                File outputFile = uploadPath.toFile();
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                outputStream.write(file.getBytes());
                outputStream.flush();
                outputStream.close();
               admin.setPicture(fileName);
               adminRepository.save(admin);
                return fileName;
            } catch (IOException e) {
                // Handle the exception
                return "Error while saving the picture";
            }
        } else {
            return "No picture found in the request";
        }
    }

    @Transactional
    @Override
    public Admin modifyAdmin(Admin admin) {

        Admin originalAdmin = adminRepository.findById(admin.getId()).orElse(null);
        assert originalAdmin != null;
        originalAdmin.setEmail(admin.getEmail());
        originalAdmin.setName(admin.getName());
        originalAdmin.setLastname(admin.getLastname());
        originalAdmin.setPhone(admin.getPhone());

        return adminRepository.save(originalAdmin);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }




}
