package io.SampleLogin.Controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import io.SampleLogin.Model.LinkedinAccount;
import io.SampleLogin.Model.User;
import io.SampleLogin.Repository.LinkedinAccountRepository;
import io.SampleLogin.Repository.UserRepository;
import io.response.CustomResponse;


@RestController
@RequestMapping("/rest/auth/linkedin")
public class LinkedInController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LinkedinAccountRepository linkedinAccountRepository;

	
	static class LinkedinRequest{
		String comment;
		Map<String, String> visibility;
		
		LinkedinRequest(String comment, String user){
			this.comment = comment;
			visibility = new HashMap<String, String>();
			visibility.put("code", user);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST)
    public String friends(@RequestParam("status") String status, Model model) throws ClientProtocolException, IOException
    {
    	LinkedinRequest obj = new LinkedinRequest(status,  "anyone");
    	Gson gson = new Gson();
    	String json = gson.toJson(obj);
   
		HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 
    	HttpPost request = new HttpPost("https://api.linkedin.com/v1/people/~/shares");
	    StringEntity params =new StringEntity(json);
	    
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = userRepository.findByUsername(username);
		int current_id = currentUser.getUser_id();
		LinkedinAccount linkedinAccount = linkedinAccountRepository.findByLinkedinId(current_id);

		
		String accessToken = linkedinAccount.getAccessToken();
		String str = "Bearer "+accessToken;
	    
	   // request.addHeader("Authorization", "Bearer AQWqiwJT5EfYalY4fiAnHwFjiCSjTMmRPaGLz3zp_IwqwErqWljKD72K2cwvIUQMHfEjkGBwEaWHSzfUQ2cTqHazk66iIht0uArMQKJYz76RwJrZuBnL5bs_hod3M1U6oseKl7Qk3-h54vcOcjVPJoWauiHxwyiG_jTyrSRkdII9mf0LIJqss8mXp56Oe2OWgOuLNOoXVY0dGMa9c2QY5e9PznMyabrxleQnujXtIpweNRG60x7mgyqLWYjukTwzPgnyIdVqT5Fle_OG-r9gP9jxVriZo-zZ1tV8o9JA8WIBu5WcTpxipdYEsTk5QC88CExOSL2LsLQiLIQx8780IPZG-wRdwg");
	    
		request.addHeader("Authorization", str);
	    
	    request.addHeader("Content-Type", "application/json"); 
	    request.addHeader("x-li-format", "json"); 
	    request.setEntity(params);
	    HttpResponse response = httpClient.execute(request);
	    
	    model.addAttribute("httpresponse",response);
	    
	    CustomResponse reply = new CustomResponse(response.getStatusLine().getReasonPhrase(),response.getStatusLine().getStatusCode(), "linkedin");
	    String json_response = gson.toJson(reply);
	    return json_response;

    }
}

