package tn.corilus.authentification.authentification.web;

import jakarta.ws.rs.NotFoundException;
import org.springframework.web.bind.annotation.*;
import tn.corilus.authentification.authentification.entities.*;
import tn.corilus.authentification.authentification.service.ConversationService;
import tn.corilus.authentification.authentification.service.MessageService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ConversationService conversationService;
    private final MessageService messageService;

    public ChatController(ConversationService conversationService, MessageService messageService) {
        this.conversationService = conversationService;
        this.messageService = messageService;
    }

    @PostMapping("/conversation/create/{doctorId}")
    public Conversation createConversation(@PathVariable Long doctorId) {
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        return conversationService.createConversation(doctor);
    }

    @PostMapping("/message/{conversationId}/{isDoctorMessage}")
    public Message sendMessage(@PathVariable Long conversationId, @PathVariable boolean isDoctorMessage, @RequestBody Message message) {
        Conversation conversation = conversationService.getConversationById(conversationId);
        if (conversation == null) {
            throw new NotFoundException("Conversation not found");
        }
        User sender = isDoctorMessage ? conversation.getDoctor() : conversation.getSupport();

        return messageService.createMessage(conversation, sender, message.getContent());
    }

}

