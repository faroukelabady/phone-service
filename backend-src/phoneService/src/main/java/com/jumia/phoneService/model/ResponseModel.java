package com.jumia.phoneService.model;

import java.util.List;

import lombok.Data;

@Data
public class ResponseModel {
	
	private long total;
	private int pageIndex;
	private int pageNumbers;
	private List<CustomerModel> result;

}
