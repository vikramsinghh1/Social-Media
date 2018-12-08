package io.SampleLogin.Provider;


 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.google.api.Google;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import io.SampleLogin.Model.User;
 

 
@Service
public class GoogleProvider   {
 
 private static final String REDIRECT_CONNECT_GOOGLE = "redirect:/login";
 private static final String GOOGLE = "google";
 
     @Autowired
     BaseProvider socialLoginBean ;
 
 
	 public String getGoogleUserData(Model model, User user) {
	 
		 ConnectionRepository connectionRepository = socialLoginBean.getConnectionRepository();
		
		 if (connectionRepository.findPrimaryConnection(Google.class) == null) {
			 return REDIRECT_CONNECT_GOOGLE;
		 }
	 
		 populateUserDetailsFromGoogle(user);
		 model.addAttribute("loggedInUser",user);
		 return "user";
	 }
 
 
	 protected void populateUserDetailsFromGoogle(User user) {
	 
		 Google google = socialLoginBean.getGoogle();
	
		 String email = google.plusOperations().getGoogleProfile().getAccountEmail();
		
		 String username = google.plusOperations().getGoogleProfile().getDisplayName();
	
		 user.setEmail(email);
	
		 user.setUsername(username);
		
		 user.setProvider(GOOGLE);
	 

	 }
 
}