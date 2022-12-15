package com.greenrent.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenrent.dto.ReservationDTO;
import com.greenrent.dto.request.ReservationRequest;
import com.greenrent.dto.request.ReservationUpdateRequest;
import com.greenrent.dto.response.CarAvailabilityResponse;
import com.greenrent.dto.response.GRResponse;
import com.greenrent.dto.response.ResponseMessage;
import com.greenrent.service.CarService;
import com.greenrent.service.ReservationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController {
	
	private ReservationService reservationService;
	private CarService carService;
	
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<GRResponse> makeReservation(HttpServletRequest request,
														@RequestParam(value = "carId") Long carId ,
														@Valid @RequestBody ReservationRequest reservationRequest){
		
		Long userId = (Long) request.getAttribute("id");	
		reservationService.createReservation(reservationRequest, userId, carId);
		
		GRResponse response = new GRResponse();
		response.setMessage(ResponseMessage.RESERVATION_CREATED_MESSAGE);
		response.setSuccess(true);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@PostMapping("/add/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<GRResponse> addReservation(@RequestParam(value = "userId") Long userId ,
													@RequestParam(value = "carId") Long carId ,
													@Valid @RequestBody ReservationRequest reservationRequest){
		
		
		reservationService.createReservation(reservationRequest, userId, carId);
		
		GRResponse response = new GRResponse();
		response.setMessage(ResponseMessage.RESERVATION_CREATED_MESSAGE);
		response.setSuccess(true);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	//admin bütün sreservasyon bilgilerini getirmek için kullanıyor
	@GetMapping("/admin/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ReservationDTO>> getAllReservations(){
		List<ReservationDTO> reservations = reservationService.getAllReservation();
		
		return ResponseEntity.ok(reservations);
	}
	
	//admin bir reservasyon id ile reservasyon bilgisini döndürmek için kullanıyor
	@GetMapping("/{id}/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id){
		
		ReservationDTO reservationDTO = reservationService.findById(id);	
		return ResponseEntity.ok(reservationDTO);
	}
	

	@PutMapping("/admin/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<GRResponse> updateReservation(
														@RequestParam(value = "carId") Long carId ,
														@RequestParam(value = "reservationId") Long reservationId,
														@Valid @RequestBody ReservationUpdateRequest reservationUpdateRequest){
		
		reservationService.updateReservation(reservationId,carId,reservationUpdateRequest);
		
		GRResponse response = new GRResponse();
		response.setMessage(ResponseMessage.RESERVATION_UPDATED_MESSAGE);
		response.setSuccess(true);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	//Bir user'a ait olan tüm reservasyonları döndürüyor
	@GetMapping("/admin/auth/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ReservationDTO>> getAllUserReservations(@RequestParam(value = "userId") Long userId){
		
		List<ReservationDTO> reservations = reservationService.findAllByUserId(userId);	
		return ResponseEntity.ok(reservations);
	}
	
	
	@GetMapping("/auth")
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<GRResponse> checkCarIsAvailable(@RequestParam(value = "carId") Long carId,
			@RequestParam(value = "pickUpDateTime") @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")LocalDateTime pickUptime,
			@RequestParam(value = "dropOffDateTime") @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")LocalDateTime dropOffTime){
		
		//True gelirse available olmuyor.
		boolean isNotAvailable = reservationService.checkCarAvailability(carId, pickUptime, dropOffTime);
		Double totalPrice = reservationService.getTotalPrice(carId, pickUptime, dropOffTime);		
		
		CarAvailabilityResponse response = new CarAvailabilityResponse(!isNotAvailable,totalPrice,ResponseMessage.CAR_AVAILABLE_MESSAGE,true);
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/admin/{id}/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<GRResponse> deleteReservation(@PathVariable Long id){
		reservationService.removeById(id);
			
		
		GRResponse response = new GRResponse();
		response.setMessage(ResponseMessage.RESERVATION_DELETED_MESSAGE);
		response.setSuccess(true);
		
		return ResponseEntity.ok(response);
	}
	
	//Kullanıcı kendisine ait olan bir reservasyon bilgisini getiriyor
	@GetMapping("/{id}/auth")
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<ReservationDTO> getUserReservationById(HttpServletRequest request,@PathVariable Long id){	
		Long userId = (Long) request.getAttribute("id");	
		ReservationDTO reservationDTO = reservationService.findByIdAndUserId(id, userId);	
		return ResponseEntity.ok(reservationDTO);
	}
	
	//kullanıcı kendisine ait olan bütünr reservasyon bilgilerini getiriyor
	@GetMapping("/auth/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<List<ReservationDTO>> getUserReservationByUserId(HttpServletRequest request){	
		Long userId = (Long) request.getAttribute("id");	
		List<ReservationDTO> reservations = reservationService.findAllByUserId(userId);	
		return ResponseEntity.ok(reservations);
	}
	
	

}
