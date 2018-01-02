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

import de.glk.projecttracker.model.Project;
import de.glk.projecttracker.repository.ProjectRepository;
import de.glk.projecttracker.ui.editor.ProjectEditor;

@SpringView(name = ProjectsView.VIEW_NAME)
public class ProjectsView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "Projects";
	private Grid<Project> grid;
	private TextField filter;
	private Button addNewBtn;
	private ProjectEditor editor;
	private ProjectRepository projectRepository;

	public ProjectsView(ProjectRepository projectRepository, ProjectEditor editor) {
		this.projectRepository = projectRepository;
		this.grid = new Grid<>(Project.class);
		this.editor = editor;
		this.filter = new TextField();
		this.addNewBtn = new Button("New Project", FontAwesome.PLUS);
	}

	@PostConstruct
	void init() {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		addComponent(mainLayout);

		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("name");

		filter.setPlaceholder("Filter by last name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listProjects(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editProject(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editProject(new Project(null, null)));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listProjects(filter.getValue());
		});

		// Initialize listing
		listProjects(null);
	}

	// tag::listProjects[]
	void listProjects(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(projectRepository.findAll());
		} else {
			grid.setItems(projectRepository.findByNameStartsWithIgnoreCase(filterText));
		}
	}
	// end::listProjects[]

	@Override
	public void enter(ViewChangeEvent event) {
		// This view is constructed in the init() method()
	}
}
