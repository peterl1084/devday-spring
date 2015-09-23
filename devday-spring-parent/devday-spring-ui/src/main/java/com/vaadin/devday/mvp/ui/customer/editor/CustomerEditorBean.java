package com.vaadin.devday.mvp.ui.customer.editor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.devday.mvp.ui.AbstractPopupView;
import com.vaadin.devday.service.customer.Customer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@Scope("prototype")
public class CustomerEditorBean extends AbstractPopupView<CustomerEditorPresenter>implements CustomerEditor {
	private FormLayout form;
	private BeanFieldGroup<Customer> fieldGroup;

	public CustomerEditorBean() {
		form = new FormLayout();
		form.setMargin(true);
		form.setSpacing(true);

		fieldGroup = new BeanFieldGroup<>(Customer.class);

		TextField firstNameField = new TextField("First name");
		TextField lastNameField = new TextField("Last name");

		form.addComponents(firstNameField, lastNameField);

		fieldGroup.bind(firstNameField, "firstName");
		fieldGroup.bind(lastNameField, "lastName");

		Button save = new Button("Save", e -> getPresenter().onFormSaveClicked());
		save.addStyleName(ValoTheme.BUTTON_PRIMARY);
		Button cancel = new Button("Cancel", e -> close());

		HorizontalLayout buttonLayout = new HorizontalLayout(cancel, save);
		buttonLayout.setSpacing(true);
		buttonLayout.setWidth(100, Unit.PERCENTAGE);
		buttonLayout.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		buttonLayout.setExpandRatio(cancel, 1);
		buttonLayout.setComponentAlignment(cancel, Alignment.TOP_RIGHT);

		VerticalLayout layout = new VerticalLayout(form, buttonLayout);
		layout.setSpacing(true);

		setHeightUndefined();

		setCompositionRoot(layout);
	}

	public void setCustomer(BeanItem<Customer> customer) {
		fieldGroup.setItemDataSource(customer);
	}

	@Override
	protected int getWindowPixelWidth() {
		return 350;
	}

	@Override
	protected boolean isModal() {
		return false;
	}

	@Override
	@Autowired
	protected void injectPresenter(CustomerEditorPresenter presenter) {
		setPresenter(presenter);
	}

	@Override
	public boolean isFormContentValid() {
		return fieldGroup.isValid();
	}

	@Override
	public Customer fetchFormContent() {
		try {
			fieldGroup.commit();
			return fieldGroup.getItemDataSource().getBean();
		} catch (CommitException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void showSaveFailed() {
		Notification.show("Saving failed", Type.ERROR_MESSAGE);
	}

	@Override
	public void showSaveSucceeded() {
		Notification.show("Data saved!", Type.TRAY_NOTIFICATION);
	}
}
