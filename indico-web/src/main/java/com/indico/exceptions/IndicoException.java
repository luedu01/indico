package com.indico.exceptions;

import java.io.Serializable;

public class IndicoException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String message;
	
	public IndicoException(String message) {
		super(message);
		this.message=message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	
} 
