package tn.corilus.authentification.authentification.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private boolean readed=false;

    private LocalDateTime timestamp;

    @ManyToOne
    @JsonIgnoreProperties({"messages"})
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @ManyToOne
    @JsonIgnoreProperties({"admin"})
    @JoinColumn(name = "sender_id")
    private User sender;

}
