package com.archiving.archiving.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class FirstNameAlreadyExist extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FirstNameAlreadyExist(String message) {
		super(message);
	}
}
