package com.catalog.com.exceptions.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String message;


	public ProductNotFoundException(String message) {
		super(message);
	}
}
