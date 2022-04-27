package com.archiving.archiving.system.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.archiving.archiving.system.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
	Boolean existsByUsername(String username);
}
