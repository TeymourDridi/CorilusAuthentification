package tn.corilus.authentification.authentification.web;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.corilus.authentification.authentification.entities.Doctor;
import tn.corilus.authentification.authentification.service.DoctorService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@RestController
public class DoctorController {


    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_DOCTOR') || hasRole('ROLE_SUPPORT')")
    @GetMapping("/doctor/{id}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable("id") Long id) {

        return new ResponseEntity<>(doctorService.getDoctor(id),
                HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_DOCTOR') || hasRole('ROLE_SUPPORT')")
    @GetMapping("/doctor/getAll/oredred-doctors-by-approved")
    public ResponseEntity<List<Doctor>> getDoctor() {

        return new ResponseEntity<>(doctorService.getAllDoctorsOrderdByApprooved(),
                HttpStatus.OK);

    }

    @GetMapping(value = "/user/picture/{pictureName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<byte[]> getImageWithMediaType(@PathVariable("pictureName") String pictureName) throws IOException {
        String imagePath = "pictures/"+pictureName; // Replace with the actual absolute file path

        File imageFile = new File(imagePath);
        if (!imageFile.exists() || imageFile.isDirectory()) {
            // Handle the case where the image file doesn't exist or is a directory
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_DOCTOR') || hasRole('ROLE_SUPPORT')")
    @PutMapping("/doctor/modify")
    public ResponseEntity<Doctor> getDoctor(@RequestBody Doctor doctor) {

        return new ResponseEntity<>(doctorService.modifyDoctor(doctor),
                HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_DOCTOR') || hasRole('ROLE_SUPPORT')")
    @PutMapping("/doctor/approove/{id}/{idAdmin}")
    public ResponseEntity<Doctor> approoveDoctor(@PathVariable("id") Long id,@PathVariable("idAdmin") Long idAdmin) {

        return new ResponseEntity<>(doctorService.approveDoctor(id,idAdmin),
                HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_DOCTOR') || hasRole('ROLE_SUPPORT')")
    @PutMapping("/doctor/block/{id}/{idAdmin}")
    public ResponseEntity<Doctor> blockDoctor(@PathVariable("id") Long id,@PathVariable("idAdmin") Long idAdmin) {

        return new ResponseEntity<>(doctorService.blockDoctor(id,idAdmin),
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_DOCTOR') || hasRole('ROLE_SUPPORT')")
    @PutMapping("/doctor/unblock/{id}/{idAdmin}")
    public ResponseEntity<Doctor> unblockDoctor(@PathVariable("id") Long id,@PathVariable("idAdmin") Long idAdmin) {

        return new ResponseEntity<>(doctorService.unblockDoctor(id,idAdmin),
                HttpStatus.OK);
    }

}
