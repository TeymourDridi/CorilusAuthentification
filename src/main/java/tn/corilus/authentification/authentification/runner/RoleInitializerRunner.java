package tn.corilus.authentification.authentification.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.entities.ERole;
import tn.corilus.authentification.authentification.entities.Role;
import tn.corilus.authentification.authentification.repository.AdminRepository;
import tn.corilus.authentification.authentification.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class RoleInitializerRunner implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder encoder;

    public RoleInitializerRunner(RoleRepository roleRepository, AdminRepository adminRepository, PasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.adminRepository = adminRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {

        for (ERole enumRole : ERole.values()) {
            if (roleRepository.findByName(enumRole).isEmpty()) {
                Role role = new Role();
                role.setName(enumRole);
                roleRepository.save(role);
            }
        }
        if(adminRepository.findAll().isEmpty()) {
            Admin utilisateur = new Admin();
            utilisateur.setUsername("teymouradmin");
            utilisateur.setEmail("teymour1@gmail.com");
            utilisateur.setPassword(encoder.encode("12345678"));
            utilisateur.setPhone("51031771");
            utilisateur.setGender("Male");
            utilisateur.setLastname("Teymour");
            utilisateur.setName("Dridi");
            utilisateur.setHirarchie("Directeur");
            Set<Role> roles = new HashSet<>();
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            utilisateur.setRoles(roles);
            adminRepository.save(utilisateur);
        }
    }

}
