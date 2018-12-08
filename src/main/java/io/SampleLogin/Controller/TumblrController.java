package io.SampleLogin.Controller;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import io.SampleLogin.Model.TumblrAccount;
import io.SampleLogin.Model.User;
import io.SampleLogin.Repository.TumblrAccountRepository;
import io.SampleLogin.Repository.UserRepository;
import io.response.CustomResponse;
import templates.TumblrTemplate;


@RestController
@RequestMapping("/rest/auth/tumblr")
public class TumblrController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TumblrAccountRepository tumblrAccountRepository;
		
		static class TumblrRequest{
			String title;
			String body;
			
			TumblrRequest(String title, String body){
				this.title = title;
				this.body = body;
			}
		}
		
		@RequestMapping(method=RequestMethod.POST)
	    public String friends(@RequestParam("status") String status, Model model) throws ClientProtocolException, IOException
	    {	
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			User currentUser = userRepository.findByUsername(username);
			int current_id = currentUser.getUser_id();
				
			TumblrAccount tumblrAccount = tumblrAccountRepository.findBytumblrId(current_id);
			String consumerKey = tumblrAccount.getConsumerKey();
			String consumerSecret = tumblrAccount.getConsumerSecret();
			String accessToken = tumblrAccount.getAccessToken();
			String accessTokenSecret = tumblrAccount.getAccessTokenSecret();
				
			TumblrTemplate tumblr = new TumblrTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
			
			RestTemplate restTemplate = tumblr.getRestTemplate();
			
			String url = "https://api.tumblr.com/v2/blog/ub-bot.tumblr.com/post";
			TumblrRequest obj = new TumblrRequest("Default Title", status);
			Gson gson = new Gson();
	    	String json = gson.toJson(obj);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("type", "text");
		    HttpEntity<String> entity = new HttpEntity<String>(json,headers);
		   
			ResponseEntity<String> en = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			model.addAttribute("httpresponse",en.getBody());
						
			CustomResponse reply = new CustomResponse(en.getStatusCode().getReasonPhrase(),en.getStatusCodeValue(), "tumblr");
			String json_response = gson.toJson(reply);
			return json_response;
	    	
	    }
	

}
