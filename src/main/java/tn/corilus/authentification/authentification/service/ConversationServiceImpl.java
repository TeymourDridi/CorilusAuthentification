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
    private final MessageRepository messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public ConversationServiceImpl(ConversationRepository conversationRepository, DoctorService doctorService, SupportService supportService, MessageRepository messageService, SimpMessagingTemplate messagingTemplate) {
        this.conversationRepository = conversationRepository;
        this.doctorService = doctorService;
        this.supportService = supportService;
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }


    @Override
    public Conversation createConversation(Doctor doctor) {
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

        return conversation;
    }

    @Override
    public Conversation getConversationById(Long conversationId) {
        return conversationRepository.findById(conversationId).orElse(null);
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
        Conversation conversation =conversationRepository.findConversationByArchivedIsFalseAndAndDoctorId(doctorId);
        if(conversation==null){
            return null;
        }else{

            return conversation;
        }
    }

}
