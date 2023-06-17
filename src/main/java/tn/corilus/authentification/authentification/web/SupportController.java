package tn.corilus.authentification.authentification.web;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.corilus.authentification.authentification.entities.Support;
import tn.corilus.authentification.authentification.service.SupportService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@RestController
public class SupportController {


    private final SupportService supportService;

    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_DOCTOR') || hasRole('ROLE_SUPPORT')")
    @GetMapping("/support/{id}")
    public ResponseEntity<Support> getSupport(@PathVariable("id") Long id) {

        return new ResponseEntity<>(supportService.getSupport(id),
                HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/support/getAll/supports")
    public ResponseEntity<List<Support>> getSupport() {

        return new ResponseEntity<>(supportService.getAllSupports(),
                HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPPORT')")
   @GetMapping(value = "/support/picture/{pictureName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
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

    @PreAuthorize("hasRole('ROLE_SUPPORT')")
    @PutMapping("/support/modify")
    public ResponseEntity<Support> getSupport(@RequestBody Support support) {

        return new ResponseEntity<>(supportService.modifySupport(support),
                HttpStatus.OK);

    }

}
