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
public class Claim implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Date postedAt;
    @UpdateTimestamp
    private Date lastUpdatedAt;

    private boolean accepted;

    private boolean treated;

    private String subject;

    @Lob
    @Column( length = 5000 )
    private String body;

    @Lob
    @Column( length = 5000 )
    private String response;

    @ManyToOne
    @JsonIgnoreProperties({"admin","conversations"})
    private Support support;

    @ManyToOne
    @JsonIgnoreProperties({"admin","doctors","supports"})
    private Admin admin;

}
