package io.SampleLogin.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.SampleLogin.Model.Post;
import io.SampleLogin.Model.User;
import io.SampleLogin.Repository.PostRepository;
import io.SampleLogin.Repository.UserRepository;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	// @RequestMapping(value="/registration",method = RequestMethod.POST)
	@GetMapping("/")

		public String index(Model model) {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		//	model.addAttribute("CurrentUserName", authentication.getName());
			
			User user = userRepository.findByUsername(authentication.getName());
			
			List<Post> posts = postRepository.findByUserId(user.getUser_id());
			Collections.sort(posts,new Comparator<Post>() {
								
				public int compare(Post o1, Post o2) {
					
					if(o2.getDate().getTime() > o1.getDate().getTime())
						return 1;
					else if(o2.getDate().getTime() == o1.getDate().getTime())
						return 0;
					else
						return -1;
				}
			});
			
			List<Post> newList = new ArrayList<Post>();
			
			for(int i =0; i< posts.size() && i < 5; i++)
				newList.add(posts.get(i));
			
			model.addAttribute("user",user);
			model.addAttribute("posts", newList);
			model.addAttribute("all_posts",posts);
		
			return "home";
		}
}
