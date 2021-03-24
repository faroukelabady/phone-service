package com.jumia.phoneService.exception;

public class NotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 713020072012338781L;

	public NotFoundException() {
		super("Couldn't find data, check country code and state");
	}
	
	public NotFoundException(String message) {
		super(message);
	}
}
