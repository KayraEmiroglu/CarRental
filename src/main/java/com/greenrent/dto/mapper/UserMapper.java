package com.greenrent.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.greenrent.domain.User;
import com.greenrent.dto.UserDTO;
import com.greenrent.dto.request.AdminUserUpdateRequest;

//obje üretip spring konteynıra koyacağımızı söylüyoyuruz
//konteynıra koyuludysa istediğimiz yerde injekte edebildiğimizi 
//söylüyoruz
@Mapper(componentModel = "spring")
public interface UserMapper {
	
	UserDTO userToUserDTO(User user);
	List<UserDTO> map(List<User> user);
	
	//Burda kendimiz setlicemiz değerleri ignore ediyoruz.
	@Mapping(target="id",ignore = true)
	@Mapping(target = "roles",ignore = true)
	User adminUserUpdateRequestToUser(AdminUserUpdateRequest request);
	

}
