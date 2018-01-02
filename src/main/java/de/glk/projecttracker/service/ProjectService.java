package de.glk.projecttracker.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.glk.projecttracker.model.Customer;
import de.glk.projecttracker.model.Project;
import de.glk.projecttracker.repository.ProjectRepository;

@Component
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project createProject(String name, Customer customer, String decription, LocalDate dueDate) {
		Project project = new Project(name, customer);
		project.setDecription(decription);
		project.setDueDate(dueDate);
		return projectRepository.save(project);
	}

}
