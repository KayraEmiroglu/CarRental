package com.greenrent.dto.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserUpdateRequest {
	
	@Size(max=50)
	@NotNull(message="Please provide yout First Name")
	private String firstName;
	
	@Size(max=50)
	@NotNull(message="Please provide yout Last Name")
	private String lastName;
	
	@Size(min=4, max=20, message="Please provide Correct Size of Password")
	@NotNull(message="Please provide your password")
	private String password;
	
	@Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", //(541) 317-8828
            message = "Please provide valid phone number")
	@Size(min=14, max=14)
	@NotNull(message = "Please provide your phone number")
	private String phoneNumber;
	
	@Size(min=5, max=20)
	@Email(message="Please provide valid e-mail")
	@NotNull(message = "Please provide your email")
	private String email;
	
	@Size(max= 250)
	@NotNull(message = "Please provide your address")
	private String address;
	
	@Size(max= 15)
	@NotNull(message = "Please provide your zip code")
	private String zipCode;
	
	private Boolean builtIn;
	
	private Set<String> roles;

}
