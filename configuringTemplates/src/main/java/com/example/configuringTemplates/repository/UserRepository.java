package com.example.configuringTemplates.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.configuringTemplates.entity.User;


public interface UserRepository extends CrudRepository<User,Integer>{
	User findByUserName(String username);
	User findByUserId(Integer userid);

}
