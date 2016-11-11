package org.launchcode.blogz.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.launchcode.blogz.models.dao.PostDao;
import org.launchcode.blogz.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController extends AbstractController {

	@Autowired // injects instance of interface listed below
	private UserDao userDao;
	
	@Autowired // injects instance of interface listed below
	private PostDao postDao;
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		
		// TODO - implement newPost
		
		// get author (user) from session
        Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userSessionKey);
        User author = userDao.findByUid(userId);

        String title = request.getParameter("title");
        String body = request.getParameter("body");
        
		model.addAttribute("author", author);
		model.addAttribute("title", title);
		model.addAttribute("body", body);
  
		String error = "Both Title and Body are Required!";
		
        if (title == "" || body == "") {
        	model.addAttribute("error", error);
        	return "newpost";
        }
        
        Post post = new Post(title, body, author);
        postDao.save(post);
        
		return "redirect:/blog"; // TODO - this redirect should go to the new post's page  		
	}
	
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, Model model) {
		
		// TODO - implement singlePost
		Post post = postDao.findByUid(uid);
		
        String title = post.getTitle();
        String body = post.getBody();
        Date created = post.getCreated();
        
		model.addAttribute("author", username);
		model.addAttribute("title", title);
		model.addAttribute("body", body);
		model.addAttribute("created", created);
		model.addAttribute("uid", uid);
		
		return "post";
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		// TODO - implement userPosts
		// get user uid
		User user = userDao.findByUsername(username);
		int uid = user.getUid();
		
		// get user's posts
		List<Post> posts = postDao.findByAuthor_uid(uid);
        
		model.addAttribute("posts", posts);
		
		return "blog";
	}
	
}
