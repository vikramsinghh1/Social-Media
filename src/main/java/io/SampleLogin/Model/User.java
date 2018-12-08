package io.SampleLogin.Model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
	
	@Id
	@GeneratedValue
	private int user_id;
	private String username;
	private String password;
	private String email;
	private String provider;
	private boolean twitter_account_id;
	private boolean linkedin_account_id;
	private boolean tumblr_account_id;
	private boolean google_account_id;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
//
//	@OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "twitter_accountId")
//	private TwitterAccount twitterAccount;
//	
//	@OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "linkedin_accountId")
//	private LinkedinAccount linkedinAccount;
//	
//	@OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "tumblr_accountId")
//	private TumblrAccount tumblrAccount;
	
	
}

