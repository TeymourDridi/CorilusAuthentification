package tn.corilus.authentification.authentification.service;

import tn.corilus.authentification.authentification.entities.Support;
import tn.corilus.authentification.authentification.entities.SupportSignUpRequest;

/**
 * Admin CRUD
 *
 * @author Teymour Dridi
 */
public interface AuthService {

   Support registerSupport(SupportSignUpRequest signUpRequest, Long idAdmin);

   String usernameOrMailAlreadyExists(SupportSignUpRequest signUpRequest);

}
