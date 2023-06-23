package tn.corilus.authentification.authentification.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.corilus.authentification.authentification.entities.Conversation;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation,Long> {

    List<Conversation> findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc(Long supportId);
    List<Conversation> findAllBySupportIdAndAndArchivedIsTrueOrderByLastUpdatedAtDesc(Long supportId);

    Conversation findConversationByArchivedIsFalseAndAndDoctorId(Long Id);
}
