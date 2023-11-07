package tn.corilus.authentification.authentification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.corilus.authentification.authentification.entities.Message;

public interface MessageRepository extends JpaRepository<Message,Long> {

}
