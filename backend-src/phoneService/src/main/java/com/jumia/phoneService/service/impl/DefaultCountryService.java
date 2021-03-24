package com.jumia.phoneService.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.jumia.phoneService.model.CountryEnum;
import com.jumia.phoneService.model.CountryModel;
import com.jumia.phoneService.service.CountryService;

@Service
public class DefaultCountryService implements CountryService {

	@Override
	public List<CountryModel> getCountries() {
		List<CountryModel> countries = new ArrayList<CountryModel>();
		Stream.of(CountryEnum.values()).forEach(c -> countries.add(new CountryModel(c.name(), c.getCountryCode())));
		return countries;
	}

}
