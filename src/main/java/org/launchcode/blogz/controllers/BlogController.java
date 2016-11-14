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
		
		// fetch users and pass to template
		List<User> users = userDao.findAll();
		model.addAttribute("users", users);
		
		return "index";
	}
	
	@RequestMapping(value = "/blog")
	public String blogIndex(Model model) {
		
		// fetch posts and pass to template
		List<Post> posts = postDao.findAll();
		model.addAttribute("posts", posts);
		
		return "blog";
	}
	
// attempt at pagination
	
//	@RequestMapping(value = "/blog")
//	public String blogIndex(HttpServletRequest request, Model model) {
//		
//		// fetch posts and pass to template
////		List<Post> posts = postDao.findAll();
//		
//		// pull page from query string
//		String pg = request.getParameter("page");
//		int page = 1;
//		if (pg != null) {
//			page = Integer.parseInt(pg);
//		}
//		
//		int offset = 0;
//		int limit = 5;
//		
//        offset = (page - 1) * limit;
//        
//        // create list of [limit] # of posts starting at [offset]
//		List<Post> posts = new List<Post>();
//		for (int i = 0; i < limit; i++) {
//			Post po = postDao.findByUid(offset + i);
//			posts.add(po);
//		}
//		
////		HttpSession session = request.getSession();
////        Integer userId = user.getUid();
////        session.setAttribute(userSessionKey, userId);
//
//		
//		Session session = factory.getCurrentSession();
//		Query query = session.createQuery("From Foo");
//		query.setFirstResult(0);
//		query.setMaxResults(10);
//		List<Foo> fooList = fooList = query.list();
//		
//		Post p = posts[0];
//		
//		// calculate total pages
//		int totalPages = (int) Math.ceil(posts.size() / limit);
//		
//		int prev_page = 0;
//		int next_page = 0;
//		
//		if (page > 1) {
//            prev_page = page - 1;
//		}
//
//		if (totalPages > page) {
//			next_page = page + 1;
//		}
//		
//		model.addAttribute("prev_page", prev_page);
//		model.addAttribute("next_page", next_page);
//		model.addAttribute("offset", offset);
//		model.addAttribute("limit", limit);
//		model.addAttribute("posts", posts);
//
//		return "blog";
//				
//	}

}
