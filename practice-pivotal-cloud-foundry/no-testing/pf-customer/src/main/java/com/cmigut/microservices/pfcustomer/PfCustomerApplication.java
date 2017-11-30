package com.cmigut.microservices.pfcustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PfCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PfCustomerApplication.class, args);
	}
}
