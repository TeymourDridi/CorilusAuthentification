package tn.corilus.authentification.authentification.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tn.corilus.authentification.authentification.entities.ERole;
import tn.corilus.authentification.authentification.entities.Role;
import tn.corilus.authentification.authentification.repository.RoleRepository;

@Component
public class RoleInitializerRunner implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializerRunner(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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
    }
}
