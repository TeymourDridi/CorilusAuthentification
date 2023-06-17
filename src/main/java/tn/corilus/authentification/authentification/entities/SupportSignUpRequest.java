package tn.corilus.authentification.authentification.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupportSignUpRequest {

    private String name;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String gender;
    private String address;
    private Double averageResponseTime;

    private Set<String> roles = new HashSet<>();




}
