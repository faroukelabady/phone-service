package com.jumia.phoneService.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.jumia.phoneService.repository.entity.Customer;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Tag("test")
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepostiory;

	@Autowired
	private JdbcTemplate template;
	
	@Test
	@DisplayName("Check default query behaviour")
	public void testDefaultSettings() throws Exception {
		assertThat(template.queryForObject("SELECT COUNT(*) from customer", Integer.class)).isGreaterThan(0);
	}

	@Test
	@Transactional
	@DisplayName("Get list of all customers Should return non Empty list")
	public void WhenFindAllThenReturnNonEmptyList() {
		List<Customer> customer = customerRepostiory.findAll();
		assertThat(customer).isNotEmpty();
	}

	@Test
	@Transactional
	@DisplayName("Get list of all customers Should return non Empty list")
	public void WhenFindByPhoneCountryLikeThenReturnNonEmptyList() throws SQLException {
		List<Customer> customer = customerRepostiory.findByPhoneStartingWith("(237)");
		assertThat(customer).isNotEmpty();
	}

}
