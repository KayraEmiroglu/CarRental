package com.greenrent.exception.message;

public class ErrorMessage {

	public final static String RESOURCE_NOT_FOUND_MESSAGE="Resource with id %d not found";
	
	
	public final static String USER_NOT_FOUND_MESSAGE="Username with email %d not found";
	
	public final static String EMAIL_ALREADY_EXIST_MESSAGE="Email: %s already exists";
	
	public final static String ROLE_NOT_FOUND_MESSAGE="ROLE with name %s not found";

	public final static String JWTTOKEN_ERROR_MESSAGE="JWT Token Validation Error: %s";
	
	public final static String NOT_PERMITTED_METHOD_MESSAGE="You dont have any permission to change this value";
	
	public final static String PASSWORD_NOT_MATHCED_MESSAGE="Your password are not matched";

	public final static String IMAGE_NOT_FOUND_MESSAGE="ImageFile with id %s not found";
	
	public final static String RESERVATION_TIME_INCORRECT_MESSAGE="Reservation pick up time or drop off time not correct";

}
