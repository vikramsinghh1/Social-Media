package io.SampleLogin.Controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.SampleLogin.Model.GoogleAccount;
import io.SampleLogin.Model.TumblrAccount;
import io.SampleLogin.Model.User;
import io.SampleLogin.Repository.GoogleAccountRepository;
import io.SampleLogin.Repository.TumblrAccountRepository;
import io.SampleLogin.Repository.UserRepository;

@RestController
@RequestMapping("/rest/auth")
public class GoogleMailController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GoogleAccountRepository googleAccountRepository;	
	
	@RequestMapping(method=RequestMethod.POST, value="/googlemail")
    public String sendMail(@RequestParam("status") String status, @RequestParam("title") String title, @RequestParam(value="email_addresses") String email_addresses) 
    {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = userRepository.findByUsername(username);
		
		int current_id = currentUser.getUser_id();
//		GoogleAccount gaccount = googleAccountRepository.findByUserId(current_id);
//		final String user_email = gaccount.getEmail();
//		final String password = gaccount.getPassword();
		
		final String user_email = "ubbuffalo.socials@gmail.com";
		final String password = "cse611cse611";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user_email, password);
			}
		  });

		try {
		//	for(String email : email_addresses) {
				Message message = new MimeMessage(session);
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email_addresses));
				message.setSubject(title);
				message.setText(status);
	
				Transport.send(message);
	
				System.out.println("Done");
	//		}

		} catch (MessagingException e) {
			return "{status_code: 410, description:'you are not authorized'}";
		}
		return "{status_code:200}";
    }
	
	
	@RequestMapping(method=RequestMethod.GET, value="/addGoogleAccount")
	public String addGoogleAccount(@RequestParam("email") String email, @RequestParam("password") String password) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		User user = userRepository.findByUsername(username);
		
		int current_id = user.getUser_id();
		GoogleAccount gaccount = new GoogleAccount();
		gaccount.setEmail(email);
		gaccount.setPassword(password);
		gaccount.setUser_id(current_id);
		
		googleAccountRepository.save(gaccount);
		return "ok";
	}
	
}