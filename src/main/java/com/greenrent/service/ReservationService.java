package com.greenrent.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.greenrent.domain.enums.ReservationStatus;
import com.greenrent.dto.request.ReservationRequest;
import com.greenrent.exception.BadRequestException;
import com.greenrent.exception.ResourceNotFoundException;
import com.greenrent.exception.message.ErrorMessage;
import com.greenrent.repository.CarRepository;
import com.greenrent.repository.ReservationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservationService {

	private ReservationRepository reservationRepository;
	
	private CarRepository  carRepository;
	
	
	public void createReservation(ReservationRequest reservationRequest,Long userId,Long carId) {
		
		checkReservationTimeIsCorrect(reservationRequest.getPickUpTime() ,reservationRequest.getDropOffTime());
		
		carRepository.findById(carId).orElseThrow(()->
		new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, carId)));
		
		
	}
	
	public boolean carAvailability(Long carId, LocalDateTime pickUpTime,LocalDateTime dropOffTime) {
		
		ReservationStatus[] status = {ReservationStatus.CANCELLED,ReservationStatus.DONE};
		
		return false;
	}
	
	
	//Bu zamanı kontrol etme işlemlerini başka sınıflarda da yapmak isteyebiliriz
	//Bu durumlarda utility sınıf oluşturup bu tip methodları koyup static yapıp kullanabiliriz.
	private void checkReservationTimeIsCorrect(LocalDateTime pickUpTime, LocalDateTime dropOffTime) {
		
		LocalDateTime now = LocalDateTime.now();
		
		if(pickUpTime.isBefore(now)){
			throw new BadRequestException(ErrorMessage.RESERVATION_TIME_INCORRECT_MESSAGE);
		}
		boolean isEqual = pickUpTime.isEqual(dropOffTime)?true:false;
		boolean isBefore = pickUpTime.isBefore(dropOffTime)?true:false;
		
		if(!isBefore||isEqual) {
			throw new BadRequestException(ErrorMessage.RESERVATION_TIME_INCORRECT_MESSAGE);
		}
		
	}
	
	
}
