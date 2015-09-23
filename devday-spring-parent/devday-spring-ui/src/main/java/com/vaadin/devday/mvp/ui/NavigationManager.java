package com.vaadin.devday.mvp.ui;

import java.util.Arrays;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import com.vaadin.devday.mvp.ui.menu.NavigationEvent;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.UriFragmentManager;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;

@UIScope
@SpringComponent
public class NavigationManager {

	@Autowired
	private SpringViewProvider viewProvider;

	@Autowired
	private ApplicationEventPublisher eventPublished;

	@Autowired
	private ApplicationViewDisplay viewDisplay;

	private Navigator navigator;

	@Autowired
	private VaadinUI ui;

	@PostConstruct
	protected void initialize() {
		navigator = new Navigator(ui, new UriFragmentHandler(Page.getCurrent()), viewDisplay);
		navigator.addProvider(viewProvider);
	}

	public void navigateTo(Views view) {
		eventPublished.publishEvent(new NavigationEvent(view));
	}

	@EventListener
	protected void onNavigationEvent(NavigationEvent event) {
		navigator.navigateTo(event.getTarget().getId());
	}

	private class UriFragmentHandler extends UriFragmentManager {

		public UriFragmentHandler(Page page) {
			super(page);
		}

		@Override
		public void uriFragmentChanged(UriFragmentChangedEvent event) {
			Optional<Views> targetView = Arrays.asList(Views.values()).stream()
					.filter(v -> v.getId().equals(getState())).findFirst();
			if (targetView.isPresent()) {
				navigateTo(targetView.get());
			} else {
				// fallback
				navigateTo(Views.CUSTOMER);
			}
		}
	}
}
