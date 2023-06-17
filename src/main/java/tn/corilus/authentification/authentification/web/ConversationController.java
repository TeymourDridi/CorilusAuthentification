package tn.corilus.authentification.authentification.web;


import jakarta.ws.rs.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.corilus.authentification.authentification.entities.*;
import tn.corilus.authentification.authentification.service.ConversationService;
import tn.corilus.authentification.authentification.service.MessageService;

import java.util.List;


@RestController
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }


    @GetMapping("/conversation/getAllConversationsBySupportId/{id}")
    public List<Conversation> sendMessage(@PathVariable Long id) {


        return conversationService.getConversationBySupportIdAndArchivedIsFalse(id);
    }


    @PutMapping("/conversation/mark-as-readed/{conversationId}")
    public ResponseEntity<String> markAllMessagesAsReaded(@PathVariable Long conversationId) {
        conversationService.markAllMessagesAsReaded(conversationId);
        return ResponseEntity.ok("All messages marked as readed");
    }

    @GetMapping("/conversation/getConversationsByDoctorId/{id}")
    public ResponseEntity<?> getConversationByDoctorId(@PathVariable Long id) {
        Conversation conversation=conversationService.getConversationByDoctorIdAndArchivedIsFalse(id);
        if(conversation==null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(conversation);
        }

    }

}

