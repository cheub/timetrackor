package de.glk.projecttracker.ui.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.util.StringUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;

import de.glk.projecttracker.model.Profile;
import de.glk.projecttracker.model.Project;
import de.glk.projecttracker.repository.ProjectRepository;
import de.glk.projecttracker.service.ProfileService;
import de.glk.projecttracker.service.ProjectService;

@SpringView(name = "")
public class HomeView extends HorizontalLayout implements View {

	private Grid<Project> grid;
	private TextField filter;
	private ProjectRepository projectRepository;
	private ProfileService profileService;

	public HomeView(ProjectRepository projectRepository, ProfileService profileService) {
		this.projectRepository = projectRepository;
		this.profileService = profileService;
		this.grid = new Grid<>(Project.class);
		this.filter = new TextField();
	}

	@PostConstruct
	void init() {

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid);
		addComponent(mainLayout);

		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("name", "dueDate");

		grid.addColumn(project -> project, project -> {
			Profile profile = profileService.getCurrentProfile();
			if (profile.getActiveProject().isPresent() && profile.getActiveProject().get().equals(project))
				return "End";
			return "Start";
		}, new ButtonRenderer<Project>(clickEvent -> {
			Profile profile = profileService.getCurrentProfile();
			if (profile.getActiveProject().isPresent() && profile.getActiveProject().get().equals(clickEvent.getItem()))
				profileService.endActiveTask();
			else
				profileService.startTask(clickEvent.getItem());
			listProjects(filter.getValue());
			
		}));

		filter.setPlaceholder("Filter by name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listProjects(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
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
