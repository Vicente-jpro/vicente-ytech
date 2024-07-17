package com.example.vicenteytech.exceptions;

public class UsuarioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsuarioException(String errorMessage) {
        super(errorMessage);
    }
}
