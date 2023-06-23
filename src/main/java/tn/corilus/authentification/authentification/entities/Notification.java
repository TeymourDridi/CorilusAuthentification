package tn.corilus.authentification.authentification.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private boolean readed;


    private String message;

    @CreationTimestamp
    private Date postedAt;
    @UpdateTimestamp
    private Date lastUpdatedAt;


    @ManyToOne
    @JsonIgnoreProperties({"admin","conversations","support","claims"})
    private User user;




}
