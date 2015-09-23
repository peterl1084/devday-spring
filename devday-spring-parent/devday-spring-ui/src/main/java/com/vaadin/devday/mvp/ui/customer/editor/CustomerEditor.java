package com.vaadin.devday.mvp.ui.customer.editor;

import com.vaadin.devday.mvp.ui.ApplicationView;
import com.vaadin.devday.service.customer.Customer;

public interface CustomerEditor extends ApplicationView<CustomerEditorPresenter> {

	boolean isFormContentValid();

	Customer fetchFormContent();

	void showSaveFailed();

	void showSaveSucceeded();

	void close();
}
