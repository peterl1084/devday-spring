package com.vaadin.devday.mvp.ui.menu;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.devday.mvp.ui.AbstractView;
import com.vaadin.devday.mvp.ui.Views;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@SpringComponent
public class ApplicationMenuBean extends AbstractView<ApplicationMenuPresenter>implements ApplicationMenu {

	private CssLayout menuLayout;
	private Label menuTitle;

	private Map<Views, Button> menuItemMap;

	public ApplicationMenuBean() {
		setSizeFull();

		menuItemMap = new HashMap<>();

		menuLayout = new CssLayout();
		menuLayout.setSizeFull();
		menuLayout.setPrimaryStyleName(ValoTheme.MENU_ROOT);

		menuTitle = new Label();

		HorizontalLayout titleHolder = new HorizontalLayout(menuTitle);
		titleHolder.setPrimaryStyleName(ValoTheme.MENU_TITLE);

		menuLayout.addComponent(titleHolder);

		setCompositionRoot(menuLayout);
	}

	@Override
	public void setMenuTitle(String title) {
		menuTitle.setValue(title);
	}

	@Override
	public void addMenuItem(Views view) {
		Button menuItem = new Button(view.getName(), view.getIcon());
		menuItem.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		menuItem.addClickListener(e -> getPresenter().onMenuItemClicked(view));
		menuLayout.addComponent(menuItem);
		menuItemMap.put(view, menuItem);
	}

	@Override
	public void markMenuItemActive(Views view) {
		menuItemMap.values().forEach(i -> i.removeStyleName("selected"));

		if (menuItemMap.containsKey(view)) {
			menuItemMap.get(view).addStyleName("selected");
		}
	}

	@Override
	@Autowired
	protected void injectPresenter(ApplicationMenuPresenter presenter) {
		setPresenter(presenter);
	}
}
