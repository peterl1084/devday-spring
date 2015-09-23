package com.vaadin.devday.mvp.ui.customer;

import javax.ejb.EJB;

import org.springframework.context.event.EventListener;

import com.vaadin.devday.mvp.ui.AbstractPresenter;
import com.vaadin.devday.mvp.ui.customer.event.CustomerEvent;
import com.vaadin.devday.service.customer.Customer;
import com.vaadin.devday.service.customer.CustomerService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class CustomerViewPresenter extends AbstractPresenter<CustomerView> {

	@EJB
	private CustomerService customerService;

	@Override
	protected void onPresenterReady() {
		super.onPresenterReady();

		getView().populateCustomers(customerService.getCustomers());
	}

	@EventListener(condition = "#event.eventType == T(com.vaadin.devday.mvp.ui.customer.event.CustomerEvent.EventType).ADD")
	public void onAddEvent(CustomerEvent event) {
		getView().openCustomerEditor(new Customer());
	}

	@EventListener(condition = "#event.eventType ==T(com.vaadin.devday.mvp.ui.customer.event.CustomerEvent.EventType).EDIT")
	public void onEditEvent(CustomerEvent event) {
		getView().openCustomerEditor(event.getTarget());
	}

	@EventListener(condition = "#event.eventType == T(com.vaadin.devday.mvp.ui.customer.event.CustomerEvent.EventType).REMOVE")
	public void onRemoveCustomerClicked(CustomerEvent event) {
		customerService.delete(event.getTarget());
		getView().populateCustomers(customerService.getCustomers());
	}
}
