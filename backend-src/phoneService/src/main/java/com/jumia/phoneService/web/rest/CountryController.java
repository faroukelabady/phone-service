package com.jumia.phoneService.web.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jumia.phoneService.model.CountryModel;
import com.jumia.phoneService.service.CountryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/country")
@Log4j2
@RequiredArgsConstructor
public class CountryController {

	private final CountryService countryService;

	@GetMapping
	public ResponseEntity<List<CountryModel>> getCountries() {
		log.info("retrieve a list of countries");
		List<CountryModel> countries = countryService.getCountries();
		return new ResponseEntity<>(countries, HttpStatus.OK);
	}
}
