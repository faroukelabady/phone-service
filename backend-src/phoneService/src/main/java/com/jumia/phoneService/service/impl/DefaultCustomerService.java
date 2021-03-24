package com.jumia.phoneService.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.jumia.phoneService.exception.NotFoundException;
import com.jumia.phoneService.mapper.CustomerMapper;
import com.jumia.phoneService.model.CountryEnum;
import com.jumia.phoneService.model.ResponseModel;
import com.jumia.phoneService.model.State;
import com.jumia.phoneService.repository.CustomerRepository;
import com.jumia.phoneService.repository.entity.Customer;
import com.jumia.phoneService.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultCustomerService implements CustomerService {

	private final CustomerRepository customerRepository;

	private final CustomerMapper customerMapper;

	@Override
	public ResponseModel findCustomers(PageRequest pageRequest) {
		Page<Customer> result = customerRepository.findAll(pageRequest);
		return createResponse(result);
	}

	@Override
	@Transactional
	public ResponseModel findCustomersByPhoneCountry(String countryCode, PageRequest pageRequest) {
		CountryEnum country = CountryEnum.fromCountryCode(countryCode);
		if (country == null) {
			throw new NotFoundException();
		}
		Page<Customer> result = customerRepository.findByPhoneStartingWith(country.getCountryCodeRegex(), pageRequest);
		return createResponse(result);
	}

	@Override
	@Transactional
	public ResponseModel findCustomersByState(State state, PageRequest pageRequest) {
		List<Customer> result = new ArrayList<Customer>();
		Stream.of(CountryEnum.values()).forEach(country -> {
			List<Customer> entities = customerRepository.findByPhoneStartingWith(country.getCountryCodeRegex());
			String validPhoneRegex = country.getValidStateRegex();
			Map<Boolean, List<Customer>> customerByState = separateCustomersByState(entities, validPhoneRegex);
			result.addAll(filterCustomersByState(customerByState, state));
		});
		Page<Customer> paginatedCustomers = new PageImpl<>(result, pageRequest, result.size());
		return createResponse(paginatedCustomers);
	}

	@Override
	@Transactional
	public ResponseModel findCustomersByPhoneCountryAndstate(String countryCode, State state, PageRequest pageRequest) {
		CountryEnum country = CountryEnum.fromCountryCode(countryCode);
		if (country == null) {
			throw new NotFoundException();
		}
		String countryRegex = country.getCountryCodeRegex();
		List<Customer> entities = customerRepository.findByPhoneStartingWith(countryRegex);
		String validRegex = CountryEnum.fromCountryCode(countryCode).getValidStateRegex();
		Map<Boolean, List<Customer>> customerByState = separateCustomersByState(entities, validRegex);
		List<Customer> result = filterCustomersByState(customerByState, state);
		Page<Customer> paginatedCustomers = new PageImpl<>(result, pageRequest, result.size());
		return createResponse(paginatedCustomers);
	}

	private Map<Boolean, List<Customer>> separateCustomersByState(List<Customer> entities, String validRegex) {
		return entities.stream().collect(Collectors.partitioningBy(c -> {
			Pattern pattern = Pattern.compile(validRegex);
			return pattern.matcher(c.getPhone()).matches();
		}));
	}

	private List<Customer> filterCustomersByState(Map<Boolean, List<Customer>> customerByState, State state) {
		List<Customer> result = new ArrayList<Customer>();
		switch (state) {
		case VALID:
			result.addAll(customerByState.get(true));
			break;
		case INVALID:
			result.addAll(customerByState.get(false));
			break;
		default:
			result.addAll(customerByState.get(true));
			result.addAll(customerByState.get(false));
		}
		return result;
	}

	private ResponseModel createResponse(Page<Customer> customers) {
		ResponseModel response = new ResponseModel();
		response.setResult(customerMapper.entityListToModelList(customers.getContent()));
		response.setPageIndex(customers.getNumber());
		response.setTotal(customers.getTotalElements());
		response.setPageNumbers(customers.getTotalPages());
		return response;
	}

}
