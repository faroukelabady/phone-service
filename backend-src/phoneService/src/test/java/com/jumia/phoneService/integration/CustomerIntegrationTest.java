package com.jumia.phoneService.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("test")
public class CustomerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	@DisplayName("GET /customer success")
	void testGetCustomerSuccess() throws Exception {
		// Execute the GET request
		mockMvc.perform(get("/customer?page=0&size=10"))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$['result']", hasSize(10))).andExpect(jsonPath("$['result'][0].name", is("Walid Hammadi")))
				.andExpect(jsonPath("$['result'][1].name", is("Yosaf Karrouch")));
	}
	
	@Test
	@DisplayName("GET /customer/country/{countryCode} success")
	void testGetCustomerByCountryCodeSuccess() throws Exception {
		// Execute the GET request
		mockMvc.perform(get("/customer/country/212?page=0&size=10"))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$['result']", hasSize(7))).andExpect(jsonPath("$['result'][0].name", is("Walid Hammadi")))
				.andExpect(jsonPath("$['result'][1].name", is("Yosaf Karrouch")));
	}
	
	@Test
	@DisplayName("GET /customer/country/{countryCode} not Found")
	void testGetCustomerByCountryCodeNotFound() throws Exception {
		// Execute the GET request
		mockMvc.perform(get("/customer/country/500?page=0&size=10"))
				// Validate the response code and content type
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	@DisplayName("GET /customer/state/{state} success")
	void testGetCustomerByStateSuccess() throws Exception {
		// Execute the GET request
		mockMvc.perform(get("/customer/state/VALID?page=0&size=10"))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$['result']", hasSize(27))).andExpect(jsonPath("$['result'][0].name", is("EMILE CHRISTIAN KOUKOU DIKANDA HONORE ")))
				.andExpect(jsonPath("$['result'][1].name", is("MICHAEL MICHAEL")));
	}
	
	@Test
	@DisplayName("GET /customer/state/{state}  all success")
	void testGetCustomerByStateALLSuccess() throws Exception {
		// Execute the GET request
		mockMvc.perform(get("/customer/state/ALL?page=0&size=10"))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$['result']", hasSize(41))).andExpect(jsonPath("$['result'][0].name", is("EMILE CHRISTIAN KOUKOU DIKANDA HONORE ")))
				.andExpect(jsonPath("$['result'][1].name", is("MICHAEL MICHAEL")));
	}
	
	@Test
	@DisplayName("GET /customer/country/{countryCode}/state/{state} success")
	void testGetCustomerByCountryCodeAndStateSuccess() throws Exception {
		// Execute the GET request
		mockMvc.perform(get("/customer/country/212/state/VALID?page=0&size=10"))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$['result']", hasSize(4))).andExpect(jsonPath("$['result'][0].name", is("Yosaf Karrouch")))
				.andExpect(jsonPath("$['result'][1].name", is("Chouf Malo")));
	}

}
