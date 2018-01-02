package de.glk.projecttracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.glk.projecttracker.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	public Optional<Profile> findByName(String name);
}
