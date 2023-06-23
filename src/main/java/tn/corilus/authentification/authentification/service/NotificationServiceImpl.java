package tn.corilus.authentification.authentification.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import tn.corilus.authentification.authentification.entities.Notification;
import tn.corilus.authentification.authentification.entities.User;
import tn.corilus.authentification.authentification.repository.NotificationRepository;
import tn.corilus.authentification.authentification.repository.UserRepository;

import java.util.List;


@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;

        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public Notification addNotification(String message,Long idUser) {
        Notification notification=new Notification();
        notification.setMessage(message);
        notification.setUser(userRepository.findById(idUser).orElse(null));
        notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/topic/notification/" + idUser, getAllNotificationsByUser(idUser));

        return notification ;
    }

    @Override
    public List<Notification> getAllNotificationsByUser(Long idUser) {

        return notificationRepository.findAllByUser(userRepository.findById(idUser).orElse(null));
    }

    @Override
    public List<Notification> notificationReaded(Long idUser) {
        User user=userRepository.findById(idUser).orElse(null);

        if (user == null) {
            return null;
        }
        user.getNotifications().forEach(notification -> notification.setReaded(true));
        userRepository.save(user);
        messagingTemplate.convertAndSend("/topic/notification/" + idUser, user.getNotifications());

        return user.getNotifications();
    }
}
