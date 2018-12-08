package io.SampleLogin.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.SampleLogin.Model.TwitterAccount;
import io.SampleLogin.Model.User;
import io.SampleLogin.Repository.TwitterAccountRepository;
import io.SampleLogin.Repository.UserRepository;


@Controller
@RequestMapping("/rest/auth")
public class TwitterAccountController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TwitterAccountRepository twitterAccountRepository;	
	
	@RequestMapping(method=RequestMethod.GET, value="/addTwitterAccount")
	public String addtwitterAccount(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		User user = userRepository.findByUsername(username);
		
		int current_id = user.getUser_id();
		
		if(user.isTwitter_account_id()) {
			TwitterAccount twitterAccount = twitterAccountRepository.findBytwitterId(current_id);
			model.addAttribute("twitterAccount", twitterAccount);
		}
		/*else {
			model.addAttribute("twitterAccount", "");

		}
		*/
		
		return "LinkTwitter";
	}

	@RequestMapping(method=RequestMethod.POST, value="/addTwitterAccount")
	public String addAuthenticationKeys(@RequestParam("consumerKey") String consumerKey, @RequestParam("consumerSecret") String consumerSecret, @RequestParam("accessToken") String accessToken, @RequestParam("accessTokenSecret") String accessTokenSecret) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		User user = userRepository.findByUsername(username);
		user.setTwitter_account_id(true);
		int current_id = user.getUser_id();
		
		TwitterAccount twitterAccount = new TwitterAccount();
		
		twitterAccount.setAccessToken(accessToken);
		twitterAccount.setAccessTokenSecret(accessTokenSecret);
		twitterAccount.setConsumerKey(consumerKey);
		twitterAccount.setConsumerSecret(consumerSecret);
		
		twitterAccount.setTwitterId(current_id);
		
		twitterAccount.setUser(user);
		
		twitterAccountRepository.save(twitterAccount);
		
		return "AddedTwitter";
	}
		
}
