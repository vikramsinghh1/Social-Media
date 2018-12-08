package io.SampleLogin.Model;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue
	private int post_id;
	
	private String postContents;
	
	private int userId;
	
	private boolean twitter;
	
	private boolean tumblr;
	
	private boolean linkedin;
	
	private Timestamp date;
}
