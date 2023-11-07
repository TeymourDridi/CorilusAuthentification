package tn.corilus.authentification.authentification.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails implements Serializable {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
    private String userName;
    private String name;
    private String lastName;
}
