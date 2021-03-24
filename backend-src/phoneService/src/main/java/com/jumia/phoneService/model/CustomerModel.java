package com.jumia.phoneService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel {

	private Integer id;
	private String name;
	private String phone;

}
