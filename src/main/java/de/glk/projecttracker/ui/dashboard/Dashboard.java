package de.glk.projecttracker.ui.dashboard;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI
@SpringViewDisplay
@Theme("valo")
public class Dashboard extends UI implements ViewDisplay {

	private Panel springViewDisplay;

	// @Autowired
	// private SpringViewProvider viewProvider;

	@Override
	protected void init(VaadinRequest request) {
		final HorizontalLayout root = new HorizontalLayout();
		root.setSizeFull();
		setContent(root);

		final CssLayout navigationBar = new CssLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		// viewProvider.getViewNamesForCurrentUI().forEach(
		// viewName ->
		// navigationBar.addComponent(createNavigationButton(getCaption(viewName),
		// viewName)));

		navigationBar.addComponent(createNavigationButton("Home", ""));
		navigationBar.addComponent(createNavigationButton("Customers", CustomerView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Projects", ProjectsView.VIEW_NAME));

		root.addComponent(navigationBar);

		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		root.addComponent(springViewDisplay);
		root.setExpandRatio(springViewDisplay, 1.0f);

	}

	private String getCaption(String viewName) {
		// TODO translate
		if (viewName.isEmpty())
			return "Home";
		return viewName;
	}

	private Button createNavigationButton(String caption, final String viewName) {
		Button button = new Button(caption);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		// If you didn't choose Java 8 when creating the project, convert this
		// to an anonymous listener class
		button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
		return button;
	}

	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
	}
}
