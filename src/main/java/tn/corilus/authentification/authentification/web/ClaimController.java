package tn.corilus.authentification.authentification.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.corilus.authentification.authentification.entities.*;
import tn.corilus.authentification.authentification.service.ClaimService;

import java.util.List;

@RestController
@RequestMapping("/claims")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getallclaimsbyadminidandacceptedisnull/{id}")
    public ResponseEntity<List<Claim>> getAllClaimsByAdminIdAndAcceptedIsNull(@PathVariable("id") Long adminId) {
        return new ResponseEntity<>(claimService.getAllClaimsByAdminIdAndAcceptedIsNull(adminId),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getallclaimsbyadminidandacceptedistrue/{id}")
    public ResponseEntity<List<Claim>> getAllClaimsByAdminIdAndAcceptedIsTrue(@PathVariable("id") Long adminId) {
        return new ResponseEntity<>(claimService.getAllClaimsByAdminIdAndAcceptedIsTrue(adminId),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getallclaimsbyadminidandacceptedisfalse/{id}")
    public ResponseEntity<List<Claim>> getAllClaimsByAdminIdAndAcceptedIsFalse(@PathVariable("id") Long adminId) {
        return new ResponseEntity<>(claimService.getAllClaimsByAdminIdAndAcceptedIsFalse(adminId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPPORT')")
    @GetMapping("/getallclaimsbysupportidandacceptedisfalse/{id}")
    public ResponseEntity<List<Claim>> getAllClaimsBySupportIdAndAcceptedIsFalse(@PathVariable("id") Long supportId) {
        return new ResponseEntity<>(claimService.getAllClaimsSupportIdAndAcceptedIsFalse(supportId),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPPORT')")
    @GetMapping("/getallclaimsbysupportidandacceptedistrue/{id}")
    public ResponseEntity<List<Claim>> getAllClaimsBySupportIdAndAcceptedIsTrue(@PathVariable("id") Long supportId) {
        return new ResponseEntity<>(claimService.getAllClaimsSupportIdAndAcceptedIsTrue(supportId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPPORT')")
    @GetMapping("/getallclaimsbysupportidandacceptedisnull/{id}")
    public ResponseEntity<List<Claim>> getAllClaimsBySupportIdAndAcceptedIsNull(@PathVariable("id") Long supportId) {

        return new ResponseEntity<>(claimService.getAllClaimsBySupportIdAndAcceptedIsNull(supportId),
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPPORT')")
    @PostMapping("/create")
    public ResponseEntity<?> createClaim(@RequestBody Claim claim) {
        Claim claimCreated=claimService.createClaim(claim);
        if(claimCreated==null){
           return  ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(claim,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClaim(@PathVariable("id") Long claimId) {
        String claimDeleted=claimService.deleteClaim(claimId);
        if(claimDeleted==null){
           return  ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(claimDeleted,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}/{situation}")
    public ResponseEntity<?> updateClaimSituation(@PathVariable("id") Long claimId,@PathVariable("situation") boolean situation) {
        Claim claimUpdated=claimService.updateSituation(claimId,situation);
        if(claimUpdated==null){
           return  ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(claimUpdated, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/addresponse")
    public ResponseEntity<?> addResponseClaim(@RequestBody Claim claim) {
        Claim claimUpdated=claimService.addResponse(claim);
        if(claimUpdated==null){
           return  ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(claimUpdated,HttpStatus.OK);
    }

}

