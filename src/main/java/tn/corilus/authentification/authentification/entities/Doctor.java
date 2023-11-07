package tn.corilus.authentification.authentification.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends User implements Serializable {

    private String niss;

    private String speciality;

    private boolean approved=false;

    private boolean blocked=false;

    @ManyToOne
    private Admin admin;

}
