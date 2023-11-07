package tn.corilus.authentification.authentification.service;

import tn.corilus.authentification.authentification.entities.Notification;

import java.util.List;

/**
 * Notification CRUD
 *
 * @author Teymour Dridi
 */
public interface NotificationService {

    Notification addNotification(String message,Long idUser);

    List<Notification> getAllNotificationsByUser(Long idUser);

    List<Notification> notificationReaded(Long idUser);

}
