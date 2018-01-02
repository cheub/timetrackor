package de.glk.projecttracker;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.glk.projecttracker.model.Address;
import de.glk.projecttracker.service.CustomerService;

@SpringBootApplication
// @EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
public class ProjectTrackerApplication {

	@Autowired
	private CustomerService customerService;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ProjectTrackerApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner() {
		return args -> initData();
	}

	private void initData() {
		Address address = new Address();
		address.setCity("Hattingen");
		address.setCountry("DE");
		address.setPostalCode("45527");
		address.setStreet("Barbarastr. 7");
		customerService.createCustomer("Mina", "Schädlich", address);
	}
}
