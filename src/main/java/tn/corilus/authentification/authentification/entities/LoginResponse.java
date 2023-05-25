package tn.corilus.authentification.authentification.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

	private Long id;

	private String username;
	private String email;
	private String name;
	private String lastname;
	private String phone;
	private String password;


	private String gender;
	private Date postedAt;
	private Date lastUpdatedAt;
	private List<String> roles;

	public LoginResponse(Long id, String username, String email, String name, String lastname, String phone, String gender, Date postedAt, Date lastUpdatedAt, List<String> roles) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.name = name;
		this.lastname = lastname;
		this.phone = phone;
		this.gender = gender;
		this.postedAt = postedAt;
		this.lastUpdatedAt = lastUpdatedAt;
		this.roles = roles;
	}
}
