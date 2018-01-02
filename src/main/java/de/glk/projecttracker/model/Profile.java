package de.glk.projecttracker.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Profile {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private LocalDateTime taskStart;
	private LocalDateTime taskEnd;

	@ManyToOne
	private Project activeProject;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getTaskStart() {
		return taskStart;
	}

	public void setTaskStart(LocalDateTime taskStart) {
		this.taskStart = taskStart;
	}

	public LocalDateTime getTaskEnd() {
		return taskEnd;
	}

	public void setTaskEnd(LocalDateTime taskEnd) {
		this.taskEnd = taskEnd;
	}

	public Project getActiveProject() {
		return activeProject;
	}

	public void setActiveProject(Project activeProject) {
		this.activeProject = activeProject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
