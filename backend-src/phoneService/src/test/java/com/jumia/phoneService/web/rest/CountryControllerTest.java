package com.jumia.phoneService.web.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
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

import com.jumia.phoneService.model.CountryModel;
import com.jumia.phoneService.service.CountryService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Tag("test")
public class CountryControllerTest {

	@MockBean
	private CountryService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("GET /Country success")
	void testGetCountrySuccess() throws Exception {
		// Setup our mocked service
		CountryModel country1 = new CountryModel("Cameroon", "237");
		CountryModel country2 = new CountryModel("Ethiopia", "251");
		doReturn(Lists.newArrayList(country1, country2)).when(service).getCountries();
		// Execute the GET request
		mockMvc.perform(get("/country"))
				// Validate the response code and content type
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].name", is("Cameroon")))
				.andExpect(jsonPath("$[1].name", is("Ethiopia")));
	}

}
