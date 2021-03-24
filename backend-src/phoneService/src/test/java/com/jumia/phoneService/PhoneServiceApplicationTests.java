package com.jumia.phoneService;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jumia.phoneService.repository.CustomerRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Tag("test")
class PhoneServiceApplicationTests {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	@DisplayName("Checking if DI works with @Autowired on Constructor")
	void checkResolverForConstructor() {
		assertThat(customerRepository).isNotNull();
	}
}
