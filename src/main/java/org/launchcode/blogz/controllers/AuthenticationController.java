package org.launchcode.blogz.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.User;
import org.launchcode.blogz.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {

	@Autowired // injects instance of interface listed below
	private UserDao userDao;
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		// implement signup

		// get params from request
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String passVer = request.getParameter("passVer");

		// validate params
		String userError = "Invalid Username";
		String passError = "Invalid Password";
		String passVerError = "Passwords Do Not Match";

		model.addAttribute("username", username);
		if (!User.isValidUsername(username)) {
			model.addAttribute("userError", userError);
			return "signup";
		}

		if (!User.isValidPassword(password)) {
			model.addAttribute("passError", passError);
			return "signup";
		}

		if (!password.equals(passVer)) {
			model.addAttribute("passVerError", passVerError);
			return "signup";
		}

		// if valid, create user and store in session
		User user = new User(username, password);
		userDao.save(user);

		// access session and login
		HttpSession session = request.getSession();
        Integer userId = user.getUid();
        session.setAttribute(userSessionKey, userId);

		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		// implement login
		// get params from request
		String username = request.getParameter("username");
		model.addAttribute("username", username);

		String password = request.getParameter("password");

		// validate entry
		String error = "Invalid Username or Password";

		// get user by username (get uid)
		User user = userDao.findByUsername(username);
		
		if (user == null) {
			model.addAttribute("error", error);
			return "login";		
		}
		
		// check password
		if (!user.isMatchingPassword(password)) {
			model.addAttribute("error", error);
			return "login";
		}

		// log in (add to seesion)
		HttpSession session = request.getSession();
        Integer userId = user.getUid();
        session.setAttribute(userSessionKey, userId);
		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
