package tn.corilus.authentification.authentification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.corilus.authentification.authentification.entities.Admin;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    @Query("SELECT a FROM Admin a WHERE SIZE(a.claims) = (SELECT MIN(SIZE(ap.claims)) FROM Admin ap)")
    List<Admin> findAdminWithLeastClaims();

}
