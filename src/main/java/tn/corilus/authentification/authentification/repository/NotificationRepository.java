package tn.corilus.authentification.authentification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.corilus.authentification.authentification.entities.Notification;
import tn.corilus.authentification.authentification.entities.User;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

List<Notification> findAllByUser(User user);

}
