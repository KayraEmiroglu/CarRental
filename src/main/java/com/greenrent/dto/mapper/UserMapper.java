package com.greenrent.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.greenrent.domain.User;
import com.greenrent.dto.UserDTO;

//obje üretip spring konteynıra koyacağımızı söylüyoyuruz
//konteynıra koyuludysa istediğimiz yerde injekte edebildiğimizi 
//söylüyoruz
@Mapper(componentModel = "spring")
public interface UserMapper {
	
	UserDTO userToUserDTO(User user);
	List<UserDTO> map(List<User> user);
	

}
