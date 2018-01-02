package de.glk.projecttracker.model;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

@Embeddable
public class TimeStamping {

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

}
