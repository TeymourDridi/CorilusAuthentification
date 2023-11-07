package tn.corilus.authentification.authentification.service;

import tn.corilus.authentification.authentification.entities.Claim;

import java.util.List;

/**
 * Claim CRUD
 *
 * @author Teymour Dridi
 */
public interface ClaimService {

    List<Claim> getAllClaimsByAdminIdAndAcceptedIsNull(Long adminId);

    List<Claim> getAllClaimsByAdminIdAndAcceptedIsFalse(Long adminId);

    List<Claim> getAllClaimsByAdminIdAndAcceptedIsTrue(Long adminId);

    List<Claim> getAllClaimsBySupportIdAndAcceptedIsNull(Long supportId);

    List<Claim> getAllClaimsSupportIdAndAcceptedIsFalse(Long supportId);

    List<Claim> getAllClaimsSupportIdAndAcceptedIsTrue(Long supportId);

    Claim createClaim(Claim claim);

    String deleteClaim(Long claimId);

    Claim updateSituation(Long claimId,boolean situation);

    Claim addResponse(Claim claim);

}
