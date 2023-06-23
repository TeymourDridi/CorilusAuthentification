package tn.corilus.authentification.authentification.service;

import org.springframework.stereotype.Service;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.entities.Claim;
import tn.corilus.authentification.authentification.repository.ClaimRepository;

import java.util.List;


@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final AdminService adminService;
    private final NotificationService notificationService;

    public ClaimServiceImpl(ClaimRepository claimRepository, AdminService adminService, NotificationService notificationService) {
        this.claimRepository = claimRepository;
        this.adminService = adminService;
        this.notificationService = notificationService;
    }


    @Override
    public List<Claim> getAllClaimsByAdminIdAndAcceptedIsNull(Long adminId) {
        return claimRepository.findAllByAdminIdAndTreatedIsFalse(adminId);
    }

    @Override
    public List<Claim> getAllClaimsByAdminIdAndAcceptedIsFalse(Long adminId) {
        return claimRepository.findAllByAdminIdAndAcceptedIsFalseAndTreatedIsTrue(adminId);
    }

    @Override
    public List<Claim> getAllClaimsByAdminIdAndAcceptedIsTrue(Long adminId) {
        return claimRepository.findAllByAdminIdAndAcceptedIsTrue(adminId);
    }

    @Override
    public List<Claim> getAllClaimsBySupportIdAndAcceptedIsNull(Long supportId) {
        return claimRepository.findAllBySupportIdAndTreatedIsFalse(supportId);
    }

    @Override
    public List<Claim> getAllClaimsSupportIdAndAcceptedIsFalse(Long supportId) {
        return claimRepository.findAllBySupportIdAndAcceptedIsFalseAndTreatedIsTrue(supportId);
    }

    @Override
    public List<Claim> getAllClaimsSupportIdAndAcceptedIsTrue(Long supportId) {
        return claimRepository.findAllBySupportIdAndAcceptedIsTrue(supportId);
    }

    @Override
    public Claim createClaim(Claim claim) {
        Admin admin=adminService.adminWithLeastClaims();
        if(admin==null){
            return null;
        }
        claim.setAdmin(admin);
        notificationService.addNotification("Une Nouvelle Reclamation a été soumise par "+ claim.getSupport().getLastname()+" "+claim.getSupport().getName(),admin.getId());

        return claimRepository.save(claim);
    }

    @Override
    public String deleteClaim(Long claimId) {
        claimRepository.deleteById(claimId);
        return "Claim Deleted" ;
    }

    @Override
    public Claim updateSituation(Long claimId, boolean situation) {
        Claim claim= claimRepository.findById(claimId).orElse(null);
        if(claim==null){
            return null;
        }
        claim.setAccepted(situation);
        claim.setTreated(true);
        return claimRepository.save(claim);
    }

    @Override
    public Claim addResponse(Claim claim) {
        Claim claim1 = claimRepository.findById(claim.getId()).orElse(null);
        if(claim1==null){
            return null;
        }
        claim1.setResponse(claim.getResponse());
        claim1.setAccepted(claim.isAccepted());
        claim1.setTreated(true);

        if(claim.isAccepted()){
            notificationService.addNotification("Votre Reclamation "+claim1.getSubject()+" a été accepter par " +claim1.getAdmin().getLastname()+" "+claim1.getAdmin().getName(),claim1.getSupport().getId());
        }else{
            notificationService.addNotification("Votre Reclamation "+claim1.getSubject()+" a été refuser par " +claim1.getAdmin().getLastname()+" "+claim1.getAdmin().getName(),claim1.getSupport().getId());

        }
        return claimRepository.save(claim1);
    }


}
