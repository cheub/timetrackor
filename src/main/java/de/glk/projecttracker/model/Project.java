package de.glk.projecttracker.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Project {

	public enum State {
		OPEN, ACTIVE, DONE
	}

	@Id
	@GeneratedValue
	private Long id;

	private State state;

	private String name;

	private String decription;

	@ManyToOne(optional = false)
	private Customer customer;

	private LocalDate dueDate;

	@ElementCollection
	private List<TimeStamping> timeStampings;

	protected Project() {
	}

	public Project(String name, Customer customer) {
		this.name = name;
		this.customer = customer;
		this.state = State.OPEN;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public List<TimeStamping> getTimeStampings() {
		return timeStampings;
	}

	public void setTimeStampings(List<TimeStamping> timeStampings) {
		this.timeStampings = timeStampings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
