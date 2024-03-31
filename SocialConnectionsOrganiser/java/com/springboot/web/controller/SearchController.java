package com.springboot.web.controller;



import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.web.dao.ContactRepository;
import com.springboot.web.dao.UserRepository;
import com.springboot.web.entities.Contact;
import com.springboot.web.entities.User;



@RestController
public class SearchController {

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value = "/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query ,Principal principal){
		System.out.println(query);
//		String name = principal.getName();
		User user = this.userRepository.getUserByUserName(principal.getName());
		List<Contact> contacts = this.contactRepository.findByNameContainingAndUser(query, user);
		return ResponseEntity.ok(contacts);
	}
}