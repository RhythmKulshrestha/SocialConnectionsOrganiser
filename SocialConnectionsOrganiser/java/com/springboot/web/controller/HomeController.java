package com.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.springboot.web.dao.UserRepository;
import com.springboot.web.entities.User;
import com.springboot.web.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@RequestMapping("/")
	public String home(Model model)
	{
		model.addAttribute("title","Home-Smart Contact Manager");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(Model model)
	{
		model.addAttribute("title","About-Smart Contact Manager");
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("title","Register-Smart Contact Manager");
		model.addAttribute("user", new User());
		return "signup";
	}
	
	//handler for registering user
	@RequestMapping(value="/do_register",method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user")User user, @RequestParam(value="agreement",defaultValue="false") boolean agreement, Model model,BindingResult result1, HttpSession session)
	{
		try {
		
		if(!agreement)
		{
			System.out.println("You have not agreed the terms and conditions");
			throw new Exception("You have not agreed the terms and conditions");
			
		}
		
		if(result1.hasErrors())
		{
			System.out.println("ERROR"+result1.toString());
			model.addAttribute("user",user);
			return "signup";
		}
		
		user.setRole("ROLE_USER");
		user.setImageUrl("default.png");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		
		System.out.println("Agreement"+ agreement);
		System.out.println("USER"+ user);
		
		
		
		User result=this.userRepository.save(user);
		System.out.println(result);
		model.addAttribute("user",new User());
		session.setAttribute("message", new Message("Successfully Registered","alert-success"));
		return "signup";		
		}
		
		catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message", new Message("Something went wrong!!"+e.getMessage(),"alert-danger"));
			return "signup";
		
		}
		
		
	}
	
	//handler for custom Login
		@GetMapping("/signin")
		public String customLogin(Model model) {
			model.addAttribute("title", "Login -Smart Contact Manager");
			return "login";
		}
	
}
	
//	@Autowired
//	private UserRepository userRepository;
//	
//	@GetMapping("/test")
//	@ResponseBody
//	@Transactional
//	public String test() {
//		
//		User user=new User();
//		user.setName("Rhythm");
//		user.setEmail("rhythm@gmailcom");
//		
//		Contact contact=new Contact();
//		user.getContacts().add(contact);
//		
//		userRepository.save(user);
//		return "working";		
//	}
	

