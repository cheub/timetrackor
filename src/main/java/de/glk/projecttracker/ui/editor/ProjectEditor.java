package de.glk.projecttracker.ui.editor;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.glk.projecttracker.model.Customer;
import de.glk.projecttracker.model.Project;
import de.glk.projecttracker.repository.CustomerRepository;
import de.glk.projecttracker.repository.ProjectRepository;

@SpringComponent
@UIScope
public class ProjectEditor extends VerticalLayout {

	private final ProjectRepository repository;

	/**
	 * The currently edited project
	 */
	private Project project;

	TextField name = new TextField("Name");
	TextField description = new TextField("Description");
	DateField dueDate = new DateField("Due date");
	ComboBox<Customer> customer = new ComboBox<>("Customer");

	/* Action buttons */
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<Project> binder = new Binder<>(Project.class);

	@Autowired
	public ProjectEditor(ProjectRepository repository, CustomerRepository customerRepository) {
		this.repository = repository;

		customer.setItems(customerRepository.findAll());
		customer.setItemCaptionGenerator(Customer::toString);

		addComponents(name, description, dueDate, customer, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> {
			repository.save(project);
		});
		delete.addClickListener(e -> repository.delete(project));
		cancel.addClickListener(e -> editProject(project));
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editProject(Project project) {
		if (project == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = project.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			this.project = repository.findOne(project.getId());
		} else {
			this.project = project;
		}
		cancel.setVisible(persisted);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(this.project);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		name.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
}
