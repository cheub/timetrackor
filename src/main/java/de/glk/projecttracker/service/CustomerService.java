package de.glk.projecttracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.glk.projecttracker.model.Address;
import de.glk.projecttracker.model.Customer;
import de.glk.projecttracker.repository.CustomerRepository;

@Component
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public Customer createCustomer(String firstName, String lastName, Address address) {
		Customer customer = new Customer(firstName, lastName);
		customer.setAddress(address);
		return customerRepository.save(customer);
	}

}
