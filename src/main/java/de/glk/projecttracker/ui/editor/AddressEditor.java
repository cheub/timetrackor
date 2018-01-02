package de.glk.projecttracker.ui.editor;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.glk.projecttracker.model.Address;

@SpringComponent
@UIScope
public class AddressEditor extends VerticalLayout {

	/**
	 * The currently edited customer
	 */
	private Address address;

	/* Fields to edit properties in Customer entity */
	TextField country = new TextField("Land");
	TextField street = new TextField("Straße");
	TextField postalCode = new TextField("PLZ");
	TextField city = new TextField("Stadt");

	Binder<Address> binder = new Binder<>(Address.class);

	@Autowired
	public AddressEditor() {

		addComponents(street, postalCode, city, country);
		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editAddress(Address address) {
		if (address == null) {
			binder.setBean(new Address());
		} else {
			binder.setBean(address);
		}

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving

		setVisible(true);

		// A hack to ensure the whole form is visible
		// Select all text in firstName field automatically
		street.selectAll();
	}

}
