package tn.corilus.authentification.authentification.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import tn.corilus.authentification.authentification.entities.*;
import tn.corilus.authentification.authentification.repository.ConversationRepository;
import tn.corilus.authentification.authentification.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final DoctorService doctorService;
    private final SupportService supportService;
    private final NotificationService notificationService;
    private final MessageRepository messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public ConversationServiceImpl(ConversationRepository conversationRepository, DoctorService doctorService, SupportService supportService, NotificationService notificationService, MessageRepository messageService, SimpMessagingTemplate messagingTemplate) {
        this.conversationRepository = conversationRepository;
        this.doctorService = doctorService;
        this.supportService = supportService;
        this.notificationService = notificationService;
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }


    @Override
    public Conversation createConversation(Doctor doctor) {
        if(getConversationByDoctorIdAndArchivedIsFalse(doctor.getId())!=null){
            return null;
        }
        Conversation conversation = new Conversation();
        Support support = supportService.getSupportWithLeastConversations();

        conversation.setDoctor(doctorService.getDoctor(doctor.getId()));
        conversation.setSupport(support);

        Message message= new Message();
        message.setContent("Bonjour je suis " +support.getLastname() +" "+support.getName()+" votre support, en quoi puis-je vous aidez");
        message.setSender(support);
        message.setTimestamp(LocalDateTime.now());
        message.setConversation(conversationRepository.save(conversation));
        conversation.getMessages().add(messageService.save(message));

        messagingTemplate.convertAndSend("/topic/chat/" + conversation.getSupport().getId(), conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc(conversation.getSupport().getId()));
        messagingTemplate.convertAndSend("/topic/doctor/" + conversation.getDoctor().getId(), getConversationByDoctorIdAndArchivedIsFalse(conversation.getDoctor().getId()));
        notificationService.addNotification("Une nouvelle conversation initier avec le docteur "+conversation.getDoctor().getLastname()+ " "+conversation.getDoctor().getName(),support.getId());
        return conversation;
    }

    @Override
    public Conversation getConversationById(Long conversationId) {
        return conversationRepository.findById(conversationId).orElse(null);
    }

    @Override
    public String deleteConversation(Long id) {
        conversationRepository.deleteById(id);
        return "Conversation Deleted";
    }

    @Override
    public List<Conversation> getConversationBySupportIdAndArchivedIsFalse(Long supportId) {
        return conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc(supportId);
    }

    @Override
    public void markAllMessagesAsReaded(Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new EntityNotFoundException("Conversation not found"));

        conversation.getMessages().forEach(message -> message.setReaded(true));
        conversationRepository.save(conversation);

        messagingTemplate.convertAndSend("/topic/chat/" + conversation.getSupport().getId(), conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc(conversation.getSupport().getId()));

    }

    @Override
    public Conversation getConversationByDoctorIdAndArchivedIsFalse(Long doctorId) {

        List<Conversation> conversations = conversationRepository.findConversationByArchivedIsFalseAndAndDoctorId(doctorId);

        if (conversations.isEmpty()) {
            return null;
        } else {
            return conversations.get(0);
        }
    }

    @Override
    public Conversation archiveConversation(Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId).orElse(null);
        conversation.setArchived(true);

        conversationRepository.save(conversation);
        messagingTemplate.convertAndSend("/topic/chat/" + conversation.getSupport().getId(), conversationRepository.findAllBySupportIdAndAndArchivedIsFalseOrderByLastUpdatedAtDesc(conversation.getSupport().getId()));
        Support support=supportService.getSupport(conversation.getSupport().getId());
        double averageDate = support.getConversations().stream()
                .mapToDouble(c -> c.getLastUpdatedAt().getTime() - c.getPostedAt().getTime())
                .average()
                .orElse(0.0);
        support.setAverageResponseTime(averageDate);
        supportService.modifySupport(support);
        return conversation;
    }

    @Override
    public List<Conversation> getConversationsBySupportIdAndArchivedIsTrue(Long supportId) {
        return conversationRepository.findAllBySupportIdAndAndArchivedIsTrueOrderByLastUpdatedAtDesc(supportId);
    }

    @Override
    public List<Conversation> getConversationsBySupportId(Long supportId) {
        return conversationRepository.findAllBySupportId(supportId);
    }

    @Override
    public List<Conversation> getConversationsByDoctorId(Long doctorId) {
        return conversationRepository.findAllByDoctorId(doctorId);
    }

}
