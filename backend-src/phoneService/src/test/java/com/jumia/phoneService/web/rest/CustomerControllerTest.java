package com.jumia.phoneService.web.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.jumia.phoneService.exception.NotFoundException;
import com.jumia.phoneService.model.CustomerModel;
import com.jumia.phoneService.model.ResponseModel;
import com.jumia.phoneService.service.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Tag("test")
public class CustomerControllerTest {
	
	@MockBean
	private CustomerService service;

	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	@DisplayName("GET /customer success")
	void testGetCustomerSuccess() throws Exception {
		// Setup our mocked service
		CustomerModel customer1 = new CustomerModel(0,"Walid Hammadi","(212) 6007989253");
		CustomerModel customer2 = new CustomerModel(1,"Yosaf Karrouch","(212) 698054317");
		ResponseModel response = new ResponseModel();
		response.setResult(Lists.newArrayList(customer1, customer2));
		doReturn(response).when(service).findCustomers(isNotNull());
		// Execute the GET request
		mockMvc.perform(get("/customer?page=0&size=10"))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$['result']", hasSize(2))).andExpect(jsonPath("$['result'][0].name", is("Walid Hammadi")))
				.andExpect(jsonPath("$['result'][1].name", is("Yosaf Karrouch")));
	}
	
	@Test
	@DisplayName("GET /customer/country/{countryCode} success")
	void testGetCustomerByCountryCodeSuccess() throws Exception {
		// Setup our mocked service
		CustomerModel customer1 = new CustomerModel(0,"Walid Hammadi","(212) 6007989253");
		CustomerModel customer2 = new CustomerModel(1,"Yosaf Karrouch","(212) 698054317");
		ResponseModel response = new ResponseModel();
		response.setResult(Lists.newArrayList(customer1, customer2));
		doReturn(response).when(service).findCustomersByPhoneCountry(isNotNull(), isNotNull());
		// Execute the GET request
		mockMvc.perform(get("/customer/country/212?page=0&size=10"))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$['result']", hasSize(2))).andExpect(jsonPath("$['result'][0].name", is("Walid Hammadi")))
				.andExpect(jsonPath("$['result'][1].name", is("Yosaf Karrouch")));
	}
	
	@Test
	@DisplayName("GET /customer/country/{countryCode} not Found")
	void testGetCustomerByCountryCodeNotFound() throws Exception {
		// Setup our mocked service
		doThrow(NotFoundException.class).when(service).findCustomersByPhoneCountry(isNotNull(), isNotNull());
		// Execute the GET request
		mockMvc.perform(get("/customer/country/212?page=0&size=10"))
				// Validate the response code and content type
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	@DisplayName("GET /customer/state/{state} success")
	void testGetCustomerByStateSuccess() throws Exception {
		// Setup our mocked service
		CustomerModel customer1 = new CustomerModel(0,"Walid Hammadi","(212) 6007989253");
		CustomerModel customer2 = new CustomerModel(1,"Yosaf Karrouch","(212) 698054317");
		ResponseModel response = new ResponseModel();
		response.setResult(Lists.newArrayList(customer1, customer2));
		doReturn(response).when(service).findCustomersByState(isNotNull(), isNotNull());
		// Execute the GET request
		mockMvc.perform(get("/customer/state/VALID?page=0&size=10"))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$['result']", hasSize(2))).andExpect(jsonPath("$['result'][0].name", is("Walid Hammadi")))
				.andExpect(jsonPath("$['result'][1].name", is("Yosaf Karrouch")));
	}
	
	@Test
	@DisplayName("GET /customer/country/{countryCode}/state/{state} success")
	void testGetCustomerByCountryCodeAndStateSuccess() throws Exception {
		// Setup our mocked service
		CustomerModel customer1 = new CustomerModel(0,"Walid Hammadi","(212) 6007989253");
		CustomerModel customer2 = new CustomerModel(1,"Yosaf Karrouch","(212) 698054317");
		ResponseModel response = new ResponseModel();
		response.setResult(Lists.newArrayList(customer1, customer2));
		doReturn(response).when(service).findCustomersByPhoneCountryAndstate(isNotNull(), isNotNull(), isNotNull());
		// Execute the GET request
		mockMvc.perform(get("/customer/country/212/state/VALID?page=0&size=10"))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$['result']", hasSize(2))).andExpect(jsonPath("$['result'][0].name", is("Walid Hammadi")))
				.andExpect(jsonPath("$['result'][1].name", is("Yosaf Karrouch")));
	}

}
