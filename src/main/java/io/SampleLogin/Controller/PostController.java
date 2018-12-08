package io.SampleLogin.Controller;


import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.SampleLogin.Model.Post;
import io.SampleLogin.Model.User;
import io.SampleLogin.Repository.PostRepository;
import io.SampleLogin.Repository.UserRepository;


@RestController
@RequestMapping("/rest/auth/updatePostData")
public class PostController {

	@Autowired
	private UserRepository userRepository;
		
	@Autowired
	private PostRepository postRepository;
	
	@RequestMapping(method=RequestMethod.POST)
    public String Posts(@RequestParam("status") String status, @RequestParam(value="social_media[]") String[] social_medias) 
    {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = userRepository.findByUsername(username);
		int current_id = currentUser.getUser_id();

		Post post = new Post();
		post.setPostContents(status);
		post.setUserId(current_id);
		post.setDate(new Timestamp(System.currentTimeMillis()));
		//post.setTwitter(1);
		for(String media : social_medias) {
			if(media.equals("tumblr"))
				post.setTumblr(true);
			if(media.equals("linkedin"))
				post.setLinkedin(true);
			if(media.equals("twitter"))
				post.setTwitter(true);
		}
		postRepository.save(post);	
		return "ok";
    }
}
