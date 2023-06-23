package tn.corilus.authentification.authentification.web;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.corilus.authentification.authentification.entities.Notification;
import tn.corilus.authentification.authentification.service.NotificationService;

import java.util.List;


@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }





    @PutMapping("/notification/mark-as-readed/{userId}")
    public ResponseEntity<String> markAllMessagesAsReaded(@PathVariable Long userId) {
        notificationService.notificationReaded(userId);
        return ResponseEntity.ok("All notifications marked as readed");
    }

    @GetMapping("/notification/getAll/{userId}")
    public ResponseEntity<List<Notification>> getAllNotification(@PathVariable Long userId) {

        return ResponseEntity.ok(notificationService.getAllNotificationsByUser(userId));
    }



}

