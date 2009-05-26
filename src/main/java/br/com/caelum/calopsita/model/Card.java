package br.com.caelum.calopsita.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Card implements Identifiable {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@Column(length=1024)
	private String description;

	@ManyToOne
	private Project project;

	@ManyToOne
	private Iteration iteration;

	@ManyToOne
	private Card parent;
	
	@OneToMany(mappedBy="parent")
	private List<Card> substories;
	
	private int priority;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.TODO;
	
	public static enum Status {
		TODO, DONE
	}
	
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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public void setIteration(Iteration iteration) {
		this.iteration = iteration;
	}

	public Iteration getIteration() {
		return iteration;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public void setParent(Card parent) {
		this.parent = parent;
	}

	public Card getParent() {
		return parent;
	}

	public void setSubstories(List<Card> substories) {
		this.substories = substories;
	}

	public List<Card> getSubstories() {
		if (substories == null) {
			substories = new ArrayList<Card>();
		}

		return substories;
	}

}
