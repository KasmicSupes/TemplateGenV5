package com.example.configuringTemplates.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.configuringTemplates.exception.AccessDeniedException;
import com.example.configuringTemplates.exception.UserNotFoundException;
import com.example.configuringTemplates.exception.WrongCredentialsException;
import com.example.configuringTemplates.repository.UserRepository;



@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
    	com.example.configuringTemplates.entity.User user = userRepository.findByUserName(username);
        if (user == null)
        {
            throw new UserNotFoundException("User with Username"+" "+username+" "+"does not exist");
        }
        if (user.getLevel().equals("NA"))
        {
            throw new AccessDeniedException("Not authorised");
        }
        else {
        
        	return new MyUserPrincipal(user);
        }
  }
	

}
