package com.greenrent.exception;



//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	/**
	 * throwable'dan geliyor Dönen exception'ın bozulup bozulmadığını kontrol ediyor
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
