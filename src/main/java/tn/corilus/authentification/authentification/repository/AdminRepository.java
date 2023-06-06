package tn.corilus.authentification.authentification.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.entities.Doctor;

public interface AdminRepository extends JpaRepository<Admin,Long> {

}
