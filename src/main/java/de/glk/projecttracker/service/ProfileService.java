package de.glk.projecttracker.service;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.glk.projecttracker.model.Profile;
import de.glk.projecttracker.model.Project;
import de.glk.projecttracker.model.TimeStamping;
import de.glk.projecttracker.repository.ProfileRepository;

@Service
@Transactional
public class ProfileService {

	@Autowired
	private ProfileRepository repository;

	@PostConstruct
	private void init() {
		if (!repository.findByName("default").isPresent()) {
			Profile profile = new Profile();
			profile.setName("default");
			repository.save(profile);
		}

	}

	public Profile getCurrentProfile() {
		return repository.findByName("default").get();
	}

	public void startTask(Project project) {
		Profile profile = repository.findByName("default").get();
		profile.setActiveProject(project);
		profile.setTaskStart(LocalDateTime.now());
		profile.setTaskEnd(null);
		repository.save(profile);
	}

	public void endActiveTask() {
		Profile profile = repository.findByName("default").get();
		if (profile.getActiveProject().isPresent()) {

			profile.setTaskEnd(LocalDateTime.now());

			TimeStamping stamping = new TimeStamping();
			stamping.setStartTime(profile.getTaskStart());
			stamping.setEndTime(profile.getTaskEnd());
			profile.getActiveProject().get().getTimeStampings().add(stamping);
			profile.setActiveProject(null);
			repository.save(profile);
		}
	}
}
