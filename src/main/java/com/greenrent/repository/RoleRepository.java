package com.greenrent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenrent.domain.Role;
import com.greenrent.domain.enums.RoleType;

public interface  RoleRepository  extends JpaRepository<Role,Integer> {

	
	Optional<Role> findByName(RoleType name);
	
	
}
