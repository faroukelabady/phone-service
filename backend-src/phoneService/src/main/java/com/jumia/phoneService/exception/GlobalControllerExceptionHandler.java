package com.jumia.phoneService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jumia.phoneService.model.RestErrorInfo;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class GlobalControllerExceptionHandler {
	
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public RestErrorInfo handleGeneralExceptions(Exception ex) {
		log.error("error during request processing", ex);
		return new RestErrorInfo("General error", ex.getMessage());
	}

	@ExceptionHandler(RequestNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public RestErrorInfo handleRequestNotFoundException(RequestNotFoundException ex) {
		log.error("error during request processing", ex);
		return new RestErrorInfo("Request not found", ex.getMessage());
	}
	
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RestErrorInfo handleNotFoundException(NotFoundException ex) {
		log.error("error during request processing", ex);
		return new RestErrorInfo("Data not found", ex.getMessage());
	}

}
