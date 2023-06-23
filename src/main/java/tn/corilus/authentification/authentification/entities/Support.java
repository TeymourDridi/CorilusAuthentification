package tn.corilus.authentification.authentification.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Support extends User implements Serializable {


    private Double averageResponseTime=0.0;
    @ManyToOne
    @JsonIgnoreProperties({"supports","claims","doctors","notifications"})
    private Admin admin;

    @OneToMany(mappedBy = "support", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"messages","doctor","admin","support"})
    private List<Conversation> conversations = new ArrayList<>();

}
