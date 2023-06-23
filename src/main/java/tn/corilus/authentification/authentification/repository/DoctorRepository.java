package tn.corilus.authentification.authentification.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.corilus.authentification.authentification.entities.Doctor;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    @Query("SELECT d from Doctor d order by d.approved")
    List<Doctor> getAllDoctorsOrderdByApprooved();

    @Query("SELECT d FROM Doctor d WHERE MONTH(d.postedAt) = MONTH(CURRENT_DATE) AND YEAR(d.postedAt) = YEAR(CURRENT_DATE)")
    List<Doctor> findDoctorsAddedThisMonth();
}
