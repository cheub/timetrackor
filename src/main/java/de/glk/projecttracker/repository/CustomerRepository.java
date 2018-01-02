package de.glk.projecttracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.glk.projecttracker.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query("select c from Customer c where c.firstName like ?1% or c.lastName like ?1% or c.company like ?1%")
	List<Customer> filter(String filterText);

}
