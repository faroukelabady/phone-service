package com.jumia.phoneService.service;

import org.springframework.data.domain.PageRequest;

import com.jumia.phoneService.model.ResponseModel;
import com.jumia.phoneService.model.State;

public interface CustomerService {

	ResponseModel findCustomers(PageRequest pageRequest);

	ResponseModel findCustomersByPhoneCountry(String countryCode, PageRequest pageRequest);
	
	ResponseModel findCustomersByState(State State, PageRequest pageRequest);
	
	ResponseModel findCustomersByPhoneCountryAndstate(String countryCode, State state, PageRequest pageRequest);

}
