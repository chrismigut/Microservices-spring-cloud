package com.cmigut.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cmigut.microservices.currencyconversionservice.bean.CurrencyConversion;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion convertCurrency(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity){
		
		CurrencyConversion conversion = new CurrencyConversion(1000L, from, to, BigDecimal.valueOf(1.2), quantity);
		conversion.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		
		return conversion;
		
	}

}
