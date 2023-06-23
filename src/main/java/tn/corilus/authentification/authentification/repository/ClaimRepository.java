package tn.corilus.authentification.authentification.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.corilus.authentification.authentification.entities.Claim;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim,Long> {


    List<Claim> findAllByAdminIdAndTreatedIsFalse(Long adminId);
    List<Claim> findAllByAdminIdAndAcceptedIsFalseAndTreatedIsTrue(Long adminId);
    List<Claim> findAllByAdminIdAndAcceptedIsTrue(Long adminId);
    List<Claim> findAllBySupportIdAndTreatedIsFalse(Long supportId);
    List<Claim> findAllBySupportIdAndAcceptedIsFalseAndTreatedIsTrue(Long supportId);
    List<Claim> findAllBySupportIdAndAcceptedIsTrue(Long supportId);
}
