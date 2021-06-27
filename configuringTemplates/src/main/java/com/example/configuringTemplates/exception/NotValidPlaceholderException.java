package com.example.configuringTemplates.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class NotValidPlaceholderException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NotValidPlaceholderException(String message) {
		super(message);
	}
}
