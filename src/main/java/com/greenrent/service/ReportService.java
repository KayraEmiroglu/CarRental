package com.greenrent.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.greenrent.domain.Car;
import com.greenrent.domain.Reservation;
import com.greenrent.domain.User;
import com.greenrent.helper.ExcelReportHelper;
import com.greenrent.repository.CarRepository;
import com.greenrent.repository.ReservationRepository;
import com.greenrent.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportService {
	
	private UserRepository userRepository;
	
	 private CarRepository carRepository;
	 
	 private ReservationRepository reservationRepository;
	 
	public ByteArrayInputStream getUserReport() throws IOException {
		List<User> users = userRepository.findAll();		
		return ExcelReportHelper.getUsersExcellReport(users);		
	}
	
	 
	public ByteArrayInputStream getCarReport() throws IOException {
		List<Car> cars = carRepository.findAll();		
		return ExcelReportHelper.getCarsExcellReport(cars);		
	}
	
	public ByteArrayInputStream getReservationReport() throws IOException {
		List<Reservation> reservations = reservationRepository.findAll();		
		return ExcelReportHelper.getReservationsExcellReport(reservations);		
	}
}
