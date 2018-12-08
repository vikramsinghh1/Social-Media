package io.SampleLogin.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.SampleLogin.Model.TumblrAccount;
import io.SampleLogin.Model.TwitterAccount;
import io.SampleLogin.Model.User;
import io.SampleLogin.Repository.TumblrAccountRepository;
import io.SampleLogin.Repository.UserRepository;

@Controller
@RequestMapping("/rest/auth")
public class TumblrAccountController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TumblrAccountRepository tumblrAccountRepository;	
	
	@RequestMapping(method=RequestMethod.GET, value="/addTumblrAccount")
	public String addtumblrAccount(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		User user = userRepository.findByUsername(username);
		
		int current_id = user.getUser_id();
		
		if(user.isTumblr_account_id()) {
			TumblrAccount tumblrAccount = tumblrAccountRepository.findBytumblrId(current_id);
			model.addAttribute("tumblrAccount", tumblrAccount);
		}
		return "linkTumblr";
	}

	@RequestMapping(method=RequestMethod.POST, value="/addTumblrAccount")
	public String addAuthenticationKeys(@RequestParam("consumerKey") String consumerKey, @RequestParam("consumerSecret") String consumerSecret, @RequestParam("accessToken") String accessToken, @RequestParam("accessTokenSecret") String accessTokenSecret) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		User user = userRepository.findByUsername(username);
		user.setTumblr_account_id(true);
		int current_id = user.getUser_id();
		
		TumblrAccount tumblrAccount = new TumblrAccount();
		
		tumblrAccount.setAccessToken(accessToken);
		tumblrAccount.setAccessTokenSecret(accessTokenSecret);
		tumblrAccount.setConsumerKey(consumerKey);
		tumblrAccount.setConsumerSecret(consumerSecret);
		
		tumblrAccount.setTumblrId(current_id);
		
		tumblrAccount.setUser(user);
		
		tumblrAccountRepository.save(tumblrAccount);
						
		return "AddedTumblr";
	}
		
}