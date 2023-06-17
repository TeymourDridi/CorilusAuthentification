package tn.corilus.authentification.authentification.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.entities.Support;
import tn.corilus.authentification.authentification.entities.SupportSignUpRequest;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Admin CRUD
 *
 * @author Teymour Dridi
 */
public interface AuthService {

   Support registerSupport(SupportSignUpRequest signUpRequest, Long idAdmin);
   String usernameOrMailAlreadyExists(SupportSignUpRequest signUpRequest);
}
