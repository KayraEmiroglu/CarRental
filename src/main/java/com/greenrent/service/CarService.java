package com.greenrent.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenrent.domain.Car;
import com.greenrent.domain.ImageFile;
import com.greenrent.dto.CarDTO;
import com.greenrent.dto.mapper.CarMapper;
import com.greenrent.exception.BadRequestException;
import com.greenrent.exception.ResourceNotFoundException;
import com.greenrent.exception.message.ErrorMessage;
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
	
	private CarMapper carMapper;
	
	//Performans olarak MapStruct en iyisi
	//ModelMapper daha  küçük yapılar için
	
	public void saveCar(CarDTO carDTO,String imageId) {
		ImageFile imFile=imageFileRepository.findById(imageId).orElseThrow(()->
		new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, imageId)));
	
//		Car car= CarMapper.Instance.carDTOtoCar(carDTO);
		
		Car car =carMapper.carDTOtoCar(carDTO);
		
		Set<ImageFile> imFiles =new HashSet<>();
		imFiles.add(imFile);	
		car.setImage(imFiles);
		
		carRepository.save(car);
	}
	
	//Bu yapı aktif bir transaction yapısına duyuyor çünkü:
	//Fetch type lazy olan bir yapıda car image bilgileri gelmedi
	//transaction açtığımız zaman lazyde olsa transaction kapanana kadar
	// istediğimiz bilgileri getirir. readOnly attribute daha verimli çalışıyor
	@Transactional(readOnly = true)
	public List<CarDTO> getAllCars(){
		List<Car> carList = carRepository.findAll();
		return carMapper.map(carList);
	}
	
	
	@Transactional(readOnly = true)
	public CarDTO findById(Long id) {
		Car car = carRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
	
		return carMapper.carToCarDTO(car);
	}
	
	@Transactional(readOnly = true)
	public Page<CarDTO> findAllWithPage(Pageable pageable){
		return carRepository.findAllCarWithPage(pageable);
	}
	
	/**
	 * this method is used to update aa car
	 * @param id --> this is Car id that will be updated.
	 * @param imageId --> this is image id
	 * @param carDTO -->this is carDTO to keep data about the car
	 */
	@Transactional
	public void updateCar(Long id,String imageId,CarDTO carDTO) {
		Car foundCar = carRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		
		ImageFile imFile = imageFileRepository.findById(imageId).orElseThrow(()->
		new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, imageId)));
		
		if(foundCar.getBuiltIn()) {
			throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
		}
		
		Set<ImageFile> imgs = foundCar.getImage();
		imgs.add(imFile);
		
		Car car = carMapper.carDTOtoCar(carDTO);
		car.setId(foundCar.getId());
		car.setImage(imgs);
		
		carRepository.save(car);	
	}
	
	public void removeById(Long id) {
		Car car = carRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		
		if(car.getBuiltIn()) {
			throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
		}
		
		carRepository.deleteById(id);
	}
	

}
