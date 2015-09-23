package com.vaadin.devday.mvp.ui.menu;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import com.vaadin.devday.mvp.ui.AbstractPresenter;
import com.vaadin.devday.mvp.ui.Views;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class ApplicationMenuPresenter extends AbstractPresenter<ApplicationMenu> {

	@Autowired
	private ApplicationEventPublisher navigationEventSource;

	@Override
	protected void onPresenterReady() {
		super.onPresenterReady();

		Arrays.asList(Views.values()).forEach(menuitem -> getView().addMenuItem(menuitem));
		getView().setMenuTitle("Vaadin - MVP example");
	}

	public void onMenuItemClicked(Views view) {
		navigationEventSource.publishEvent(new NavigationEvent(view));
	}

	@EventListener
	protected void onNavigationEvent(NavigationEvent event) {
		getView().markMenuItemActive(event.getTarget());
	}
}
