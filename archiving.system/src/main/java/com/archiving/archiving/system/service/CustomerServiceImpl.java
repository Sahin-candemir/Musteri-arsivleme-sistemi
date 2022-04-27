package com.archiving.archiving.system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archiving.archiving.system.dto.CustomerDto;
import com.archiving.archiving.system.exception.FirstNameAlreadyExist;
import com.archiving.archiving.system.exception.ResourceNotFounException;
import com.archiving.archiving.system.model.Customer;
import com.archiving.archiving.system.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<CustomerDto> getAllCustomer() {

		List<Customer> customers = customerRepository.findAll();
		return customers.stream().map(customer->mapToDto(customer)).collect(Collectors.toList());
	}

	@Override
	public Customer createCustomer(Customer customer) {
		if(customerRepository.existsByFirstName(customer.getFirstName()))
			throw new FirstNameAlreadyExist("firstName already exists");
		Customer newCustomer = customerRepository.save(customer);
		return newCustomer;
	}

	@Override
	public Customer updateCustomer(Long id, Customer newCustomer) {
		Customer customer = customerRepository.findById(id).orElseThrow(()-> new ResourceNotFounException("Customer not found with id"));
		customer.setFirstName(newCustomer.getFirstName());
		customer.setLastName(newCustomer.getLastName());
		customer.setEmail(newCustomer.getEmail());
		customerRepository.save(customer);
		return customer;
	}

	@Override
	public void deleteCustomer(Long id) {
		Customer customer = customerRepository.findById(id).orElseThrow(()-> new ResourceNotFounException("Customer not found with id"));
		customerRepository.delete(customer);
	}

	@Override
	public Customer getCustomerById(Long id) {
		Customer customer = customerRepository.findById(id).orElseThrow(()-> new ResourceNotFounException("Customer not found with id"));
		return customer;
	}
	
	private CustomerDto mapToDto(Customer customer) {
		CustomerDto customerDto=new CustomerDto();
		customerDto.setId(customer.getId());
		customerDto.setFirstName(customer.getFirstName());
		customerDto.setLastName(customer.getLastName());
	    customerDto.setEmail(customer.getEmail());
		
		return customerDto;
	}

}
