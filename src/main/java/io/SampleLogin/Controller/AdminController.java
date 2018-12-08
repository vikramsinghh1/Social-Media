package io.SampleLogin.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.SampleLogin.Model.User;
import io.SampleLogin.Repository.UserRepository;

@Controller
@RequestMapping("/test/auth")
public class AdminController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	@RequestMapping(value="/registration",method = RequestMethod.GET)
	public String registration() {
		return "registration";
	}
	
	@RequestMapping(value="/registration",method = RequestMethod.POST)
	public String saveUser(@RequestParam("username") String username,@RequestParam("email") String email,@RequestParam("password") String password) {
		
		User user = new User();
		
		user.setEmail(email);
	    user.setUsername(username);
	   // user.setUser_id(userId);
		String encryptPwd = passwordEncoder.encode(password);
		user.setPassword(encryptPwd);
		userRepository.save(user);
		return "RegistrationSuccess";
	}
	
	 @RequestMapping(value = "/checkUser", method = RequestMethod.GET)
	 public String checkUser(Model model) {
		 return "user";
	 }

}