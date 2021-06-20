package com.example.configuringTemplates.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class DuplicateKeysFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DuplicateKeysFoundException(String message) {
		super(message);
	}
}
