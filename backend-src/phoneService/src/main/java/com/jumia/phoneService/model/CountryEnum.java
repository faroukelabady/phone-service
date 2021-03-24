package com.jumia.phoneService.model;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CountryEnum {

	Cameroon("237", "(237)", "\\(237\\)\\ ?[2368]\\d{7,8}$"),
	Ethiopia("251", "(251)", "\\(251\\)\\ ?[1-59]\\d{8}$"),
	Morocco("212", "(212)", "\\(212\\)\\ ?[5-9]\\d{8}$"),
	Mozambique("258", "(258)", "\\(258\\)\\ ?[28]\\d{7,8}$"),
	Uganda("256", "(256)", "\\(256\\)\\ ?\\d{9}$");

	private final String countryCode;
	private final String countryCodeRegex;
	private final String validStateRegex;

	private static final Map<String, CountryEnum> LOOKUP = Stream.of(values())
			.collect(Collectors.toMap(CountryEnum::getCountryCode, x -> x));

	private CountryEnum(String countryCode, String countryCodeRegex, String validStateRegex) {
		this.countryCode = countryCode;
		this.countryCodeRegex = countryCodeRegex;
		this.validStateRegex = validStateRegex;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getCountryCodeRegex() {
		return countryCodeRegex;
	}

	public String getValidStateRegex() {
		return validStateRegex;
	}

	public static CountryEnum fromCountryCode(String countryCode) {
		return LOOKUP.get(countryCode);
	}

}
