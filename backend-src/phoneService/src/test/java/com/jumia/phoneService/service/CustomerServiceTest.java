package com.jumia.phoneService.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.jumia.phoneService.exception.NotFoundException;
import com.jumia.phoneService.mapper.CustomerMapper;
import com.jumia.phoneService.mapper.CustomerMapperImpl;
import com.jumia.phoneService.model.ResponseModel;
import com.jumia.phoneService.model.State;
import com.jumia.phoneService.repository.CustomerRepository;
import com.jumia.phoneService.repository.entity.Customer;
import com.jumia.phoneService.service.impl.DefaultCustomerService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Tag("test")
public class CustomerServiceTest {

	@Mock
	CustomerRepository customerRepository;

	@Spy
	CustomerMapper mapper = new CustomerMapperImpl();

	@InjectMocks
	DefaultCustomerService customerService;

	@Test
	@DisplayName("get list of all customers should return non empty list")
	public void WhenFindAllThenReturnNonEmptyList() {
		mockCustomerEntityList();
		PageRequest pageRequest = PageRequest.of(0, 15);
		ResponseModel response = customerService.findCustomers(pageRequest);
		assertThat(response.getResult()).isNotEmpty();
		verify(customerRepository).findAll(any(Pageable.class));
	}

	@ParameterizedTest
	@ValueSource(strings = { "212", "258", "237" })
	@DisplayName("given valid customer phone when find then return customer model")
	public void givenCountryCodeWhenFindThenReturnCustomer(String phoneCountry) throws SQLException {
		mockCustomerRepositoryFindPhoneStartingWithPaging();
		PageRequest pageRequest = PageRequest.of(0, 15);
		ResponseModel response = customerService.findCustomersByPhoneCountry(phoneCountry, pageRequest);
		assertThat(response.getResult()).isNotNull();
		assertThat(response.getResult().size()).isEqualTo(1);
		verify(customerRepository).findByPhoneStartingWith(isNotNull(), any(Pageable.class));
	}

	@ParameterizedTest
	@ValueSource(strings = { "333" })
	@DisplayName("given Invalid phone country code name when find then throw NotFoundException")
	public void givenCountryCodeWhenFindThenThrowException(String phoneCountryCode) {
		PageRequest pageRequest = PageRequest.of(0, 15);
		Throwable thrown = catchThrowable(() -> {
			customerService.findCustomersByPhoneCountry(phoneCountryCode, pageRequest);
		});
		assertThat(thrown).isInstanceOf(NotFoundException.class);
		verify(customerRepository, times(0)).findByPhoneStartingWith(any(), any(Pageable.class));
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "VALID"})
	@DisplayName("given valid phone state when find then return customer model")
	public void givenValidstateWhenFindThenReturnCustomer(String state) throws SQLException {
		mockCustomerRepositoryFindPhoneStartingWith();
		PageRequest pageRequest = PageRequest.of(0, 15);
		ResponseModel response = customerService.findCustomersByState(State.valueOf(state), pageRequest);
		assertThat(response.getResult()).isNotNull();
		assertThat(response.getResult().size()).isEqualTo(1);
		verify(customerRepository, times(5)).findByPhoneStartingWith(isNotNull());
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "INVALID"})
	@DisplayName("given invalid phone state when find then return customer model")
	public void givenInvalidStateWhenFindThenReturnCustomer(String state) throws SQLException {
		mockCustomerRepositoryFindPhoneStartingWith();
		PageRequest pageRequest = PageRequest.of(0, 15);
		ResponseModel response = customerService.findCustomersByState(State.valueOf(state), pageRequest);
		assertThat(response.getResult()).isNotNull();
		assertThat(response.getResult().size()).isEqualTo(2);
		verify(customerRepository, times(5)).findByPhoneStartingWith(isNotNull());
	}
	
	@Test
	@DisplayName("given valid phone state and country code when find then return customer model")
	public void givenValidstateAndCountryCodeWhenFindThenReturnCustomer() throws SQLException {
		mockCustomerRepositoryFindPhoneStartingWith();
		PageRequest pageRequest = PageRequest.of(0, 15);
		ResponseModel response = customerService.findCustomersByPhoneCountryAndstate("258",State.valueOf("VALID"), pageRequest);
		assertThat(response.getResult()).isNotNull();
		assertThat(response.getResult().size()).isEqualTo(1);
		verify(customerRepository, times(1)).findByPhoneStartingWith(isNotNull());
	}


	private void mockCustomerEntityList() {
		Answer<Page<Customer>> answer = new Answer<Page<Customer>>() {
			@Override
			public Page<Customer> answer(InvocationOnMock invocation) throws Throwable {
				List<Customer> customers = new ArrayList<>();
				customers.add(createCustomerEntity(0, "Walid Hammadi", "(212) 6007989253").get());
				customers.add(createCustomerEntity(9, "sevilton sylvestre", "(258) 849181828").get());
				customers.add(createCustomerEntity(37, "WILLIAM KEMFANG", "(237) 6622284920").get());
				return new PageImpl<>(customers);
			}
		};
		doAnswer(answer).when(customerRepository).findAll(any(Pageable.class));
	}

	private void mockCustomerRepositoryFindPhoneStartingWith() {
		Answer<List<Customer>> answer = new Answer<List<Customer>>() {
			@Override
			public List<Customer> answer(InvocationOnMock invocation) throws Throwable {
				String countryCode = invocation.getArgument(0, String.class);
				return mockCustomerListFindByCountryCode(countryCode);
			}
		};
		doAnswer(answer).when(customerRepository).findByPhoneStartingWith(any());
	}
	
	private void mockCustomerRepositoryFindPhoneStartingWithPaging() {
		Answer<Page<Customer>> answer = new Answer<Page<Customer>>() {
			@Override
			public Page<Customer> answer(InvocationOnMock invocation) throws Throwable {
				String countryCode = invocation.getArgument(0, String.class);
				return new PageImpl<Customer>(mockCustomerListFindByCountryCode(countryCode));
			}
		};
		doAnswer(answer).when(customerRepository).findByPhoneStartingWith(any(), any(Pageable.class));
	}
	
	private List<Customer> mockCustomerListFindByCountryCode(String countryCode) {
		List<Customer> result = new ArrayList<Customer>();
		if (countryCode == null) {
			result =  Collections.emptyList();
		}
		switch (countryCode) {
		case "(212)":
			result = Collections
					.singletonList(createCustomerEntity(0, "Walid Hammadi", "(212) 6007989253").get());
			break;
		case "(258)":
			result = Collections
					.singletonList(createCustomerEntity(9, "sevilton sylvestre", "(258) 849181828").get());
			break;
		case "(237)":
			result = Collections
					.singletonList(createCustomerEntity(37, "WILLIAM KEMFANG", "(237) 6622284920").get());
			break;
		default:
			result = Collections.emptyList();
		}
		return result;
	}

	private Optional<Customer> createCustomerEntity(Integer id, String name, String phone) {
		Customer customer = new Customer(id, name, phone);
		return Optional.of(customer);
	}

}
