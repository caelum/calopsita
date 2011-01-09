package br.com.caelum.calopsita.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import br.com.caelum.calopsita.plugins.kanban.KanbanCard;
import br.com.caelum.calopsita.repository.Columns;

@Entity
public class Column {

	@Id @GeneratedValue
	private Long id;
	
	private String name;
	
	@ManyToOne
	private Project project;

	@Transient
	private Columns columns;
	
	@OneToMany(mappedBy = "column")
	private List<KanbanCard> cards;

	public Column(String name, Project project, Columns columns) {
		this.name = name;
		this.project = project;
		this.columns = columns;
	}
	
	Column() {
	}

	public String getName() {
		return this.name;
	}

    public void setName(String name) {
		this.name = name;
	}

	public void save() {
    	columns().add(this);
    }
    
    private Columns columns() {
    	if (columns == null) {
    		throw new IllegalStateException("Repository was not set. You should inject it first");
    	}
    	return columns;
    }

	public Project getProject() {
		return this.project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}

	public void setCards(List<KanbanCard> cards) {
		this.cards = cards;
	}

	public List<KanbanCard> getCards() {
		return cards;
	}

}

