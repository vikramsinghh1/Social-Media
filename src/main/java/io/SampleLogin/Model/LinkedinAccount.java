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
public class LinkedinAccount {
	
	@Id
	private int linkedinId;
	private String accessToken;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "linkedinId", referencedColumnName="user_id")
	private User user;
}
