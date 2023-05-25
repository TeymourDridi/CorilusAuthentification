package tn.corilus.authentification.authentification.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.corilus.authentification.authentification.entities.Role;
import tn.corilus.authentification.authentification.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    User findByPassword(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

   // @Query("select u from Utilisateur u , u.roles r, PersonnelMedical p where 'ROLE_ADMIN' in (r.name)")
    List<User> findUsersByRoles(Optional<Role> u);

    /*@Override
    Iterable<User> findAll();*/

    /*@Query("select c from Consultant c")
    List<Consultant> findConsultants();

    @Query("select c from Consultant c where c.id = ?1")
    Consultant findConsultantById(Long id);


    @Query("select p from PersonnelMedical p")
    List<PersonnelMedical> findPersonnelMedicals();

    @Query("select c from PersonnelMedical c where c.id = ?1")
    PersonnelMedical findPersonnelMedicalById(Long id);*/
}
