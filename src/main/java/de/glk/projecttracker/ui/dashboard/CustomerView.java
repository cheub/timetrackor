package de.glk.projecttracker.ui.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.util.StringUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.glk.projecttracker.model.Customer;
import de.glk.projecttracker.repository.CustomerRepository;
import de.glk.projecttracker.ui.editor.CustomerEditor;

@SpringView(name = CustomerView.VIEW_NAME)
public class CustomerView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "Customers";
	private CustomerRepository customerRepository;
	private Grid<Customer> grid;
	private CustomerEditor editor;
	private TextField filter;
	private Button addNewBtn;

	public CustomerView(CustomerRepository customerRepository, CustomerEditor editor) {
		this.customerRepository = customerRepository;
		this.grid = new Grid<>(Customer.class);
		this.editor = editor;
		this.filter = new TextField();
		this.addNewBtn = new Button("New customer", FontAwesome.PLUS);
	}

	@PostConstruct
	void init() {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		addComponent(mainLayout);

		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("lastName", "firstName", "company");

		filter.setPlaceholder("Filter by last name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCustomer(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		// Initialize listing
		listCustomers(null);
	}

	// tag::listCustomers[]
	void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(customerRepository.findAll());
		} else {
			grid.setItems(customerRepository.filter(filterText));
		}
	}
	// end::listCustomers[]

	@Override
	public void enter(ViewChangeEvent event) {
		// This view is constructed in the init() method()
	}
}
