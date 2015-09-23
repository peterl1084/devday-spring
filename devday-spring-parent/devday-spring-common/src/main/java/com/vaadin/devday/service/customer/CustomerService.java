package com.vaadin.devday.service.customer;

import java.util.List;

public interface CustomerService {

	void store(Customer customer);

	void delete(Customer customer);

	List<Customer> getCustomers();
}
