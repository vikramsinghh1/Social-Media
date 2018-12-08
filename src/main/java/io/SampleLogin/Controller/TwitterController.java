package io.SampleLogin.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import io.SampleLogin.Model.TwitterAccount;
import io.SampleLogin.Model.User;
import io.SampleLogin.Repository.TwitterAccountRepository;
import io.SampleLogin.Repository.UserRepository;
import io.response.CustomResponse;
import templates.TwitTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@RestController
@RequestMapping("/rest/auth/twitter")
public class TwitterController {
	
	static class TwitterRequest{
		String status;
		TwitterRequest(String status){
			this.status = status;
		}
		
	}
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TwitterAccountRepository twitterAccountRepository;


	@RequestMapping(method=RequestMethod.POST)
	public String helloTwitter(@RequestParam("status") String status) {
		
		CustomResponse reply = null;
		Gson gson = new Gson();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			User currentUser = userRepository.findByUsername(username);
			int current_id = currentUser.getUser_id();
			TwitterAccount twitterAccount = twitterAccountRepository.findBytwitterId(current_id);
			String consumerKey = twitterAccount.getConsumerKey();
			String consumerSecret = twitterAccount.getConsumerSecret();
			String accessToken = twitterAccount.getAccessToken();
			String accessTokenSecret = twitterAccount.getAccessTokenSecret();
	
			TwitTemplate twitter = new TwitTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
			RestTemplate restTemplate = twitter.getRestTemplate();
			
			String url = "https://api.twitter.com/1.1/statuses/update.json?status="+status;
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
		    HttpEntity<String> entity = new HttpEntity<String>(headers);
		   
			ResponseEntity<String> en = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

			reply = new CustomResponse(en.getStatusCode().getReasonPhrase(),en.getStatusCodeValue(), "twitter");
		}
		catch(HttpClientErrorException exception) {
			reply = new CustomResponse(exception.getStatusText(), exception.getRawStatusCode(), "twitter");
		}
		String json_response = gson.toJson(reply);
		return json_response;
	}
	
  
}
