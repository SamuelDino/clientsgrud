package com.samueldino.clientsgrud.services.exceptions;

public class NotSuchElementException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NotSuchElementException(String msg) {
		super(msg);
	}
}
