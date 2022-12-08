package com.greenrent.dto.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.greenrent.domain.Car;
import com.greenrent.domain.ImageFile;
import com.greenrent.dto.CarDTO;

//componentModel koyulmasını sebebi:
//Container tarafından bir bean oluşturulsun ve konulsun.
@Mapper(componentModel = "spring")
//@Mapper
public interface CarMapper {
	
	//singleton bir yapısı var
//	CarMapper Instance = Mappers.getMapper(CarMapper.class);

	@Mapping(target = "image",ignore = true)
	Car carDTOtoCar(CarDTO carDTO);
	
	//Bu yapıdan bu yapıya çevirirken ismini benim vericem bi methodu kullan
	@Mapping(source = "image",target = "image", qualifiedByName = "getImageAsString")
	CarDTO cartoCarDTO(Car car);
	
	@Named("getImageAsString")
	public static Set<String> getImageId(Set<ImageFile> images){
		Set<String> imgs = new HashSet<>();
		images.stream().map(image-> image.getId().toString())
		.collect(Collectors.toSet());
		
		return imgs;
	}
	
	
	List<CarDTO> map(List<Car> cars);
	

}
