package com.greenrent.service;

import org.springframework.stereotype.Service;

import com.greenrent.repository.CarRepository;
import com.greenrent.repository.ImageFileRepository;

import lombok.AllArgsConstructor;

//@Component scan yaparken container tarafından bulunup app contexten atılıp
//sonrasında kullanılmasını sağlıyordu.@Service daha özelleşmiş hali.

@Service
@AllArgsConstructor
public class CarService {
	
	private CarRepository carRepository;
	
	private ImageFileRepository imageFileRepository;
	

}
