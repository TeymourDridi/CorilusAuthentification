package tn.corilus.authentification.authentification.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.corilus.authentification.authentification.entities.ERole;
import tn.corilus.authentification.authentification.entities.Role;
import tn.corilus.authentification.authentification.entities.Support;
import tn.corilus.authentification.authentification.entities.SupportSignUpRequest;
import tn.corilus.authentification.authentification.repository.RoleRepository;
import tn.corilus.authentification.authentification.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;


@Service
public class AuthServiceImpl implements AuthService {

    private final AdminService adminService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;


    public AuthServiceImpl(AdminService adminService, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public String usernameOrMailAlreadyExists(SupportSignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return "Error: Username is already taken!";
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return "Error: Email is already in use!";
        }
        return "Valid";
    }
    @Override
    public Support registerSupport(SupportSignUpRequest signUpRequest, Long idAdmin) {



        // Create new support's account
        Support utilisateur = new Support();
        utilisateur.setUsername( signUpRequest.getUsername());
        utilisateur.setEmail( signUpRequest.getEmail());
        utilisateur.setPassword( encoder.encode(signUpRequest.getPassword()));
        utilisateur.setPhone( signUpRequest.getPhone());
        utilisateur.setGender( signUpRequest.getGender());
        utilisateur.setLastname( signUpRequest.getLastname());
        utilisateur.setName( signUpRequest.getName());
        utilisateur.setAverageResponseTime( signUpRequest.getAverageResponseTime());
        utilisateur.setAddress( signUpRequest.getAddress());
        utilisateur.setAdmin( adminService.getAdmin(idAdmin));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role utilisateurRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(utilisateurRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case "ROLE_SUPPORT" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_SUPPORT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case "ROLE_DOCTOR" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_DOCTOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                }
            });
        }

        utilisateur.setRoles(roles);



        return userRepository.save(utilisateur);
    }


}
