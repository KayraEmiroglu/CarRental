package com.greenrent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greenrent.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {

	 Optional<User>findByEmail(String email);
	 Boolean existsByEmail(String email);
	 
	 //JpaRepository içinde custom query ile dml operation yapılıyorsa Updating annotasyonu konulmalı
	 @Modifying
	 @Query("UPDATE User u SET u.firstName=:firstName , u.lastName=:lastName , u.phoneNumber=:phoneNumber"
	 		+ ", u.email=:email, u.address=:address , u.zipCode=:zipCode WHERE u.id=:id")
	 void update(@Param("id") Long id, 
			 @Param("firstName") String firstName,
			 @Param("lastName") String lastName,
			 @Param("phoneNumber")String phoneNumber,
			 @Param("email")String email,
			 @Param("address")String address,
			 @Param("zipCode")String zipCode);
	 
	 
	 
	
	
}
