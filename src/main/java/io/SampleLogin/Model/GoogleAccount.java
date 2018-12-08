package io.SampleLogin.Model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GoogleAccount {
	
	@Id
	private int id;
	private String email;
	private String password;
	private int user_id;
	
//	@OneToint One(cascade = CascadeType.ALL)
//	@JoinColumn(name = "user_id", referencedColumnName="user_id")
//	private User user;
	
}
