package tn.corilus.authentification.authentification.service;

import org.springframework.web.multipart.MultipartFile;
import tn.corilus.authentification.authentification.entities.Admin;
import tn.corilus.authentification.authentification.entities.Conversation;
import tn.corilus.authentification.authentification.entities.Doctor;
import tn.corilus.authentification.authentification.entities.Support;

import java.io.IOException;
import java.util.List;

/**
 * Conversation CRUD
 *
 * @author Teymour Dridi
 */
public interface ConversationService {

    Conversation createConversation(Doctor doctor);
    Conversation getConversationById(Long conversationId);
    List<Conversation> getConversationBySupportIdAndArchivedIsFalse(Long supportId);

    void markAllMessagesAsReaded(Long conversationId);

    Conversation getConversationByDoctorIdAndArchivedIsFalse(Long doctorId);

}
