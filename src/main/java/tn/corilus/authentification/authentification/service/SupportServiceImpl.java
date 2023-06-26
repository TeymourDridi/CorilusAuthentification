package tn.corilus.authentification.authentification.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tn.corilus.authentification.authentification.entities.Support;
import tn.corilus.authentification.authentification.repository.SupportRepository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
public class SupportServiceImpl implements SupportService {

    private final SupportRepository supportRepository;

    public SupportServiceImpl(SupportRepository supportRepository) {
        this.supportRepository = supportRepository;
    }


    @Override
    public Support getSupport(Long id) {
        return supportRepository.findById(id).orElse(null);
    }

    @Override
    public String addPicture(Long idUser, MultipartFile file) throws IOException {

        Support support = supportRepository.findById(idUser).orElse(null);
        if (support == null) {
            return "Doctor not found";
        }
        String fileName=support.getUsername()+"."+file.getContentType().replaceAll("image/","");
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
               support.setPicture(fileName);
                supportRepository.save(support);
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
    public Support modifySupport(Support support) {

        Support originalSupport = supportRepository.findById(support.getId()).orElse(null);
        assert originalSupport != null;
        originalSupport.setEmail(support.getEmail());
        originalSupport.setName(support.getName());
        originalSupport.setLastname(support.getLastname());
        originalSupport.setPhone(support.getPhone());
        originalSupport.setAverageResponseTime(support.getAverageResponseTime());

        return supportRepository.save(originalSupport);
    }

    @Override
    public List<Support> getAllSupports() {
        return supportRepository.findAll();
    }

    @Override
    public Support getSupportWithLeastConversations() {
        List<Support> supports = supportRepository.findSupportWithLeastConversations();

        if (supports.isEmpty()) {
            return null;
        } else {
            return supports.get(0);
        }
    }


}
