package de.glk.projecttracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.glk.projecttracker.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findByNameStartsWithIgnoreCase(String filterText);

}
