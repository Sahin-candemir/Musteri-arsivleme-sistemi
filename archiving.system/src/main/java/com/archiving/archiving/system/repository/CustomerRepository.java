package com.archiving.archiving.system.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.archiving.archiving.system.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	Boolean existsByFirstName(String firstName);
}
