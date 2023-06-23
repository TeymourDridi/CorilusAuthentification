package tn.corilus.authentification.authentification.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends User {


    private String hirarchie;

    @OneToMany(mappedBy = "admin")
    @JsonIgnore
    private List<Doctor> doctors = new ArrayList<>();

    @OneToMany(mappedBy = "admin")
    @JsonIgnoreProperties({"admin","conversations"})
    private List<Support> supports = new ArrayList<>();

    @OneToMany(mappedBy = "admin")
    @JsonIgnoreProperties({"admin","support"})
    private List<Claim> claims = new ArrayList<>();

}
