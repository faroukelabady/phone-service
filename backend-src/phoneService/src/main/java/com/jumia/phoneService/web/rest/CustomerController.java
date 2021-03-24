package com.jumia.phoneService.web.rest;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jumia.phoneService.model.ResponseModel;
import com.jumia.phoneService.model.State;
import com.jumia.phoneService.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/customer")
@Log4j2
@RequiredArgsConstructor
public class CustomerController {
	
	private final CustomerService customerService;
	
	@GetMapping(params = { "page", "size" })
	public ResponseEntity<ResponseModel> getAllCustomers(@RequestParam("page") int page, 
			  @RequestParam("size") int size) {
		log.info("retrieve a list of customers");
		PageRequest pageRequest = PageRequest.of(page, size);
		ResponseModel customers = customerService.findCustomers(pageRequest);
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}
	
	@GetMapping(path = "/country/{countryCode}", params = { "page", "size" })
	public ResponseEntity<ResponseModel> getCustomersByCountryCode(@PathVariable("countryCode") String countryCode, @RequestParam("page") int page, 
			  @RequestParam("size") int size) {
		log.info("retrieve a list of Customrs by country code");
		PageRequest pageRequest = PageRequest.of(page, size);
		ResponseModel customers = customerService.findCustomersByPhoneCountry(countryCode, pageRequest);
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}
	
	@GetMapping(path = "/state/{state}", params = { "page", "size" })
	public ResponseEntity<ResponseModel> getCustomersByState(@PathVariable("state") State state, @RequestParam("page") int page, 
			  @RequestParam("size") int size) {
		log.info("retrieve a list of Customrs by state");
		PageRequest pageRequest = PageRequest.of(page, size);
		ResponseModel customers = customerService.findCustomersByState(state, pageRequest);
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}
	
	@GetMapping(path = "/country/{countryCode}/state/{state}" , params = { "page", "size" })
	public ResponseEntity<ResponseModel> getCustomersByCountryCodeAndState(@PathVariable("countryCode") String countryCode,
			@PathVariable("state") State state, @RequestParam("page") int page, 
			  @RequestParam("size") int size) {
		log.info("retrieve a list of Customrs by country code and state");
		PageRequest pageRequest = PageRequest.of(page, size);
		ResponseModel customers = customerService.findCustomersByPhoneCountryAndstate(countryCode, state, pageRequest);
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

}
