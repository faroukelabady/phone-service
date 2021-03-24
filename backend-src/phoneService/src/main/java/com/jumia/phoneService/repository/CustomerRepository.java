package com.jumia.phoneService.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jumia.phoneService.repository.entity.Customer;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Integer> {

	Page<Customer> findByPhoneStartingWith(String countryCode, Pageable page);
	
	List<Customer> findByPhoneStartingWith(String countryCode);
	
	Page<Customer> findAll(Pageable page);
}
