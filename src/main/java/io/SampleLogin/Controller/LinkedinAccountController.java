package io.SampleLogin.Controller;

import java.sql.Blob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.SampleLogin.Model.LinkedinAccount;
import io.SampleLogin.Model.TumblrAccount;
import io.SampleLogin.Model.User;
import io.SampleLogin.Repository.LinkedinAccountRepository;
import io.SampleLogin.Repository.UserRepository;

@Controller
@RequestMapping("/rest/auth")
public class LinkedinAccountController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LinkedinAccountRepository linkedinAccountRepository;	
	
	@RequestMapping(method=RequestMethod.GET, value="/addLinkedinAccount")
	public String addlinkedinAccount(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		User user = userRepository.findByUsername(username);
		
		int current_id = user.getUser_id();
		
		if(user.isLinkedin_account_id()) {
			LinkedinAccount linkedinAccount = linkedinAccountRepository.findByLinkedinId(current_id);
			model.addAttribute("linkedinAccount", linkedinAccount);
		}
		return "linklinkedin";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/addLinkedinAccount")
	public String addAuthenticationKeys(@RequestParam("accessToken") String accessToken){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		User user = userRepository.findByUsername(username);
		user.setLinkedin_account_id(true);
		int current_id = user.getUser_id();
		
		LinkedinAccount linkedinAccount = new LinkedinAccount();
		
		//String data = "hello world";
		
		linkedinAccount.setAccessToken(accessToken);
			
		linkedinAccount.setLinkedinId(current_id);
		
		linkedinAccount.setUser(user);
		
		linkedinAccountRepository.save(linkedinAccount);

		return "AddedLinkedin";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/updateLinkedinAccount")
	public String updateAuthenticationKeys(Model model){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		User user = userRepository.findByUsername(username);
		user.setLinkedin_account_id(true);
		int current_id = user.getUser_id();
		
		LinkedinAccount linkedinAccount = linkedinAccountRepository.findByLinkedinId(current_id);
		
		model.addAttribute("accessToken", linkedinAccount.getAccessToken());
		
		return "UpdateLinkedin";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/updateLinkedinAccount")
	public String updateAuthenticationKeys(@RequestParam("accessToken") String accessToken){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		User user = userRepository.findByUsername(username);
		user.setLinkedin_account_id(true);
		int current_id = user.getUser_id();
		
		LinkedinAccount linkedinAccount = linkedinAccountRepository.findByLinkedinId(current_id);
		
		linkedinAccount.setAccessToken(accessToken);
		
		linkedinAccountRepository.save(linkedinAccount);

		return "AddedLinkedin";
	}
	
}
