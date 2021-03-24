package com.jumia.phoneService.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.jumia.phoneService.model.CustomerModel;
import com.jumia.phoneService.repository.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	CustomerModel entityToModel(Customer entity);

	Customer modelToEntity(CustomerModel model);

	List<CustomerModel> entityListToModelList(List<Customer> entityList);

	List<Customer> ModelListToEntityList(List<CustomerModel> modelList);

}
