package com.example.vicenteytech.exceptions;

public class OrderException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public OrderException(String errorMessage) {
		super(errorMessage);
	}
}
