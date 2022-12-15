package com.greenrent.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarAvailabilityResponse extends GRResponse {
	
	private double totalPrice;
	private boolean available;
	public CarAvailabilityResponse(boolean available,  double totalPrice,String message, boolean success) {
		super(success, message);
		this.totalPrice = totalPrice;
		this.available = available;
	}
	
	

}
