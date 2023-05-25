package tn.corilus.authentification.authentification.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tn.corilus.authentification.authentification.entities.Doctor;
import tn.corilus.authentification.authentification.service.DoctorService;



@RestController
public class DoctorController {


    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_DOCTOR') || hasRole('ROLE_SUPPORT')")
    @GetMapping("/doctor/{id}")
    public ResponseEntity<Doctor> getZones(@PathVariable("id") Long id) {

        return new ResponseEntity<>(doctorService.getDoctor(id),
                HttpStatus.OK);

    }

}
