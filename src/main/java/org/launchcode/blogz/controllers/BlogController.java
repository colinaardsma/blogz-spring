package org.launchcode.blogz.controllers;

import java.util.List;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.launchcode.blogz.models.dao.PostDao;
import org.launchcode.blogz.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogController extends AbstractController {

	@Autowired // injects instance of interface listed below
	private UserDao userDao;
	
	@Autowired // injects instance of interface listed below
	private PostDao postDao;
	
	@RequestMapping(value = "/")
	public String index(Model model){
		
		// TODO - fetch users and pass to template
		List<User> users = userDao.findAll();
		model.addAttribute("users", users);
		
		return "index";
	}
	
	@RequestMapping(value = "/blog")
	public String blogIndex(Model model) {
		
		// TODO - fetch posts and pass to template
		List<Post> posts = postDao.findAll();
		model.addAttribute("posts", posts);
		
		return "blog";
	}
	
}
