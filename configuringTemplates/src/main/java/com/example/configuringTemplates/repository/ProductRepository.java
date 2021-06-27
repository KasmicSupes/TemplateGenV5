package com.example.configuringTemplates.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.configuringTemplates.entity.Product;

public interface ProductRepository extends CrudRepository<Product,Integer>{
	
	Product findByProductName(String name);
}