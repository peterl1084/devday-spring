package com.vaadin.devday.mvp.ui.customer.editor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.devday.mvp.ui.AbstractPresenter;
import com.vaadin.devday.service.customer.Customer;
import com.vaadin.devday.service.customer.CustomerService;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@Scope(value = "prototype")
public class CustomerEditorPresenter extends AbstractPresenter<CustomerEditor> {

	@Autowired
	private CustomerService customerService;

	public void onCancelClicked() {
	}

	public void onFormSaveClicked() {
		if (getView().isFormContentValid()) {
			Customer customer = getView().fetchFormContent();
			try {
				customerService.store(customer);
				getView().showSaveSucceeded();
				getView().close();
			} catch (Exception e) {
				e.printStackTrace();
				getView().showSaveFailed();
			}
		}
	}
}
