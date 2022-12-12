package com.greenrent.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.greenrent.dto.response.GRResponse;
import com.greenrent.dto.response.ResponseMessage;
import com.greenrent.service.ReservationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController {
	
	private ReservationService reservationService;
	
	
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
	
	
	@GetMapping("/admin/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ReservationDTO>> getAllReservations(){
		List<ReservationDTO> reservations = reservationService.getAllReservation();
		
		return ResponseEntity.ok(reservations);
	}
	
	
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
	
	
	@GetMapping("/admin/auth/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ReservationDTO>> getAllUserReservations(@RequestParam(value = "userId") Long userId){
		
		List<ReservationDTO> reservations = reservationService.findAllByUserId(userId);	
		return ResponseEntity.ok(reservations);
	}

}
