package com.example.vicenteytech.exceptions;

public class MunicipioException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public MunicipioException(String errorMessage) {
		super(errorMessage);
	}
}
