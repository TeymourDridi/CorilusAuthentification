package tn.corilus.authentification.authentification.service;

import org.springframework.web.multipart.MultipartFile;
import tn.corilus.authentification.authentification.entities.Support;

import java.io.IOException;
import java.util.List;

/**
 * Support CRUD
 *
 * @author Teymour Dridi
 */
public interface SupportService {

    Support getSupport(Long id);

    String addPicture(Long idUser, MultipartFile file) throws IOException;

    Support modifySupport(Support support);

    List<Support> getAllSupports();

    Support getSupportWithLeastConversations();

    String deleteSupport(Long id);

}
