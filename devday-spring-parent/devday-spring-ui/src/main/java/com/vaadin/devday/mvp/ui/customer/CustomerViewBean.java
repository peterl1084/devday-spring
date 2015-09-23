package com.vaadin.devday.mvp.ui.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.devday.mvp.ui.AbstractView;
import com.vaadin.devday.mvp.ui.customer.editor.CustomerEditorBean;
import com.vaadin.devday.mvp.ui.customer.event.CustomerEvent;
import com.vaadin.devday.mvp.ui.customer.event.CustomerEvent.EventType;
import com.vaadin.devday.service.customer.Customer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = "")
public class CustomerViewBean extends AbstractView<CustomerViewPresenter>implements CustomerView, View {

	private Table customerTable;

	@Autowired
	private ApplicationEventPublisher customerEventSource;

	@Autowired
	@Lazy
	private CustomerEditorBean customerEditor;

	private Action.Handler handler = new Handler() {

		@Override
		public Action[] getActions(Object target, Object sender) {
			if (target == null) {
				return new Action[] { new CustomerEvent(EventType.ADD, null) };
			} else {
				return new Action[] { new CustomerEvent(EventType.EDIT, (Customer) target),
						new CustomerEvent(EventType.REMOVE, (Customer) target) };
			}
		}

		@Override
		public void handleAction(Action action, Object sender, Object target) {
			CustomerEvent event = (CustomerEvent) action;
			customerEventSource.publishEvent(event);
		}
	};

	public CustomerViewBean() {
		setSizeFull();

		customerTable = buildCustomerTable();
		customerTable.setSizeFull();

		VerticalLayout layout = new VerticalLayout(customerTable);
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();

		setCompositionRoot(layout);
	}

	private Table buildCustomerTable() {
		Table table = new Table();
		table.setContainerDataSource(new BeanItemContainer<Customer>(Customer.class));

		table.addActionHandler(handler);
		return table;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		getPresenter().onViewEnter();
	}

	@Override
	public void populateCustomers(List<Customer> customers) {
		Container container = customerTable.getContainerDataSource();
		customers.forEach(c -> container.addItem(c));
	}

	@Override
	public void openCustomerEditor(Customer customer) {
		BeanItem<Customer> customerBeanItem = (BeanItem<Customer>) customerTable.getContainerDataSource()
				.getItem(customer);
		customerEditor.setCustomer(customerBeanItem == null ? new BeanItem<Customer>(customer) : customerBeanItem);
	}

	@Override
	@Autowired
	protected void injectPresenter(CustomerViewPresenter presenter) {
		setPresenter(presenter);
	}
}
