package de.glk.projecttracker.ui.editor;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.glk.projecttracker.model.Address;
import de.glk.projecttracker.model.Customer;
import de.glk.projecttracker.repository.CustomerRepository;

@SpringComponent
@UIScope
public class CustomerEditor extends VerticalLayout {

	private final CustomerRepository repository;

	private final AddressEditor addressEditor;

	/**
	 * The currently edited customer
	 */
	private Customer customer;

	TextField firstName = new TextField("First name");
	TextField lastName = new TextField("Last name");
	TextField company = new TextField("Company");
	TextField phone = new TextField("Phone");
	TextField email = new TextField("Email");

	/* Action buttons */
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<Customer> binder = new Binder<>(Customer.class);

	@Autowired
	public CustomerEditor(CustomerRepository repository, AddressEditor addressEditor) {
		this.repository = repository;
		this.addressEditor = addressEditor;

		addComponents(lastName, firstName, company, addressEditor, phone, email, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> {
			repository.save(customer);
		});
		delete.addClickListener(e -> repository.delete(customer));
		cancel.addClickListener(e -> editCustomer(customer));
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editCustomer(Customer c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			customer = repository.findOne(c.getId());
		} else {
			customer = c;
		}
		cancel.setVisible(persisted);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(customer);
		if (customer.getAddress() == null)
			customer.setAddress(new Address());
		addressEditor.editAddress(customer.getAddress());

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		lastName.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
