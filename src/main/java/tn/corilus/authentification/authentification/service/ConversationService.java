package tn.corilus.authentification.authentification.service;

import tn.corilus.authentification.authentification.entities.Conversation;
import tn.corilus.authentification.authentification.entities.Doctor;

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

    Conversation archiveConversation(Long conversationId);
    List<Conversation> getConversationsBySupportIdAndArchivedIsTrue(Long supportId);

}
