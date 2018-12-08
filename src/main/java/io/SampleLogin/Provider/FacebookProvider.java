package io.SampleLogin.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import io.SampleLogin.Model.User;

@Service
public class FacebookProvider  {

	private static final String FACEBOOK = "facebook";
	private static final String REDIRECT_LOGIN = "redirect:/login";

    	@Autowired
    	BaseProvider baseProvider ;
    	


	protected void populateUserDetailsFromFacebook(User user) {
		Facebook facebook = baseProvider.getFacebook();
		
		String email = facebook.userOperations().getUserProfile().getEmail();
		
		String username = facebook.userOperations().getUserProfile().getFirstName();
		
		user.setEmail(email);
		
		user.setUsername(username);
		
		user.setProvider(FACEBOOK);
	}

	public String getFacebookUserData(Model model, User user) {
		ConnectionRepository connectionRepository = baseProvider.getConnectionRepository();
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			return REDIRECT_LOGIN;
		}
		populateUserDetailsFromFacebook(user);
		model.addAttribute("loggedInUser",user);
		return "user";
	}

	 

}
