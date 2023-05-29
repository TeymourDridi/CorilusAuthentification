package tn.corilus.authentification.authentification.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.corilus.authentification.authentification.entities.Doctor;
import tn.corilus.authentification.authentification.entities.LoginRequest;
import tn.corilus.authentification.authentification.entities.LoginResponse;
import tn.corilus.authentification.authentification.repository.RoleRepository;
import tn.corilus.authentification.authentification.repository.UserRepository;
import tn.corilus.authentification.authentification.security.jwt.JwtUtils;
import tn.corilus.authentification.authentification.service.DoctorService;
import tn.corilus.authentification.authentification.service.UserDetailsImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/user")
@RestController
public class DoctorController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
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

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        if (!userRepository.existsByUsername(loginRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Nom d'utilisateur inexistant!");
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        String jwt = jwtUtils.generateTokenFromUsername(userDetails.getUsername(),userDetails.getAuthorities());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        return ResponseEntity.ok()
                .body(new LoginResponse(jwt,userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        userDetails.getName(),
                        userDetails.getLastname(),
                        userDetails.getPhone(),
                        userDetails.getGender(),
                        userDetails.getPostedAt(),
                        userDetails.getLastUpdatedAt(),
                        roles));

    }

}
