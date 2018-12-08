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
public class TwitterAccount {
	
	@Id
	private int twitterId;
	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String accessTokenSecret;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "twitterId", referencedColumnName="user_id")
	private User user;
	
}
