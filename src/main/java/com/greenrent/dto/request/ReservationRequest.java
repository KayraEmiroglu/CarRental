package com.greenrent.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
	
	//DTO ların bir neden oluşturulma nedeni ihtiyacımız olan datayı dönmek
	//fazladan field oluşturmamak.
	 
	 @JsonFormat(shape = JsonFormat .Shape.STRING,pattern = "MM/dd/yyyy HH:mm:ss",timezone = "Turkey")
	 @NotNull(message = "Please provide the pick up time of the reservation")
	 private LocalDateTime pickUpTime;

	 @JsonFormat(shape = JsonFormat .Shape.STRING,pattern = "MM/dd/yyyy HH:mm:ss",timezone = "Turkey")
	 @NotNull(message = "Please provide the drop off time of the reservation")
	 private LocalDateTime dropOffTime;
	 
	 @Size(max = 150 ,message = "Pick Up location mst be max 150 chars")
	 @NotNull(message = "Please provide the Pick up Location time of the reservation")
	 private String pickUpLocation;

	 @Size(max = 150 ,message = "Drop off location mst be max 150 chars")
	 @NotNull(message = "Please provide the Drop off Location time of the reservation")
	 private String dropOffLocation;
	 
}
