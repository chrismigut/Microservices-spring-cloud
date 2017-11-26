package com.cmigut.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmigut.microservices.limitsservice.bean.LimitsConfiguration;
import com.cmigut.microservices.limitsservice.configuration.Configuration;

@RestController
public class LimitsConfigurationController {
	
	@Autowired
	Configuration configuration;

	@GetMapping("/limits")
	public LimitsConfiguration retrieveLimitsFromConfigurations(){
		
		return new LimitsConfiguration(configuration.getMaximum(), configuration.getMinimum());
	}
	
}
