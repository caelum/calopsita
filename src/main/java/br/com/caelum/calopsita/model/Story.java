package br.com.caelum.calopsita.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Story implements Identifiable {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String description;

	@ManyToOne
	private Project project;

	@ManyToOne
	private Iteration iteration;

	@ManyToOne
	private User owner;
	
	private int priority;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Project getProject() {
		return this.project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}

	public User getOwner() {
		return this.owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}

<<<<<<< HEAD:src/main/java/br/com/caelum/calopsita/model/Story.java
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
=======
	public void setIteration(Iteration iteration) {
		this.iteration = iteration;
	}

	public Iteration getIteration() {
		return iteration;
>>>>>>> 82905b9f896556d390fdf492c2fec862074f67de:src/main/java/br/com/caelum/calopsita/model/Story.java
	}

}
