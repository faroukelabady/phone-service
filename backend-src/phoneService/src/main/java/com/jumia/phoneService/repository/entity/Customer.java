package com.jumia.phoneService.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
	
	@Id
	private Integer id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "phone")
	private String phone;


}
