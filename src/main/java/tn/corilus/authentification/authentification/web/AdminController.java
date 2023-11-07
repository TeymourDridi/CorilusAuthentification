package tn.corilus.authentification.authentification.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.service.AdminService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/{id}")
    public ResponseEntity<Admin> getAdmin(@PathVariable("id") Long id) {
        return new ResponseEntity<>(adminService.getAdmin(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/getAll/admins")
    public ResponseEntity<List<Admin>> getAdmin() {
        return new ResponseEntity<>(adminService.getAllAdmins(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPPORT')")
    @GetMapping(value = "/admin/picture/{pictureName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<byte[]> getImageWithMediaType(@PathVariable("pictureName") String pictureName) throws IOException {
        String imagePath = "pictures/"+pictureName;

        File imageFile = new File(imagePath);
        if (!imageFile.exists() || imageFile.isDirectory()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/modify")
    public ResponseEntity<Admin> getAdmin(@RequestBody Admin admin) {
        return new ResponseEntity<>(adminService.modifyAdmin(admin),HttpStatus.OK);
    }

}
