package com.archiving.archiving.system.service;

import java.util.List;

import com.archiving.archiving.system.dto.CustomerDto;
import com.archiving.archiving.system.model.Customer;

public interface CustomerService {

	//asdf
	List<CustomerDto> getAllCustomer();

	Customer createCustomer(Customer customer);

	Customer updateCustomer(Long id, Customer newCustomer);

	void deleteCustomer(Long id);

	Customer getCustomerById(Long id);

}
