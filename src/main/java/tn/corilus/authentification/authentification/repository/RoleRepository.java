package tn.corilus.authentification.authentification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.corilus.authentification.authentification.entities.ERole;
import tn.corilus.authentification.authentification.entities.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(ERole name);

}
