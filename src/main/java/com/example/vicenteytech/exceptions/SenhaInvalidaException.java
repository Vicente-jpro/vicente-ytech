package com.example.vicenteytech.exceptions;

public class SenhaInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SenhaInvalidaException() {
        super("Senha inválida");
    }
}
