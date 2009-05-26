package br.com.caelum.calopsita.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import br.com.caelum.calopsita.model.Card.Status;

@Entity
public class Iteration implements Identifiable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Project project;

    private String goal;
    
    @OneToMany(mappedBy="iteration")
    @OrderBy("priority")
    private List<Card> cards;

    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    private LocalDate startDate;

    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    private LocalDate endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public String getFormattedStartDate() {
    	if (startDate == null) {
			return "";
		}
    	return startDate.toString("MM/dd/yyyy");
    }
    public String getFormattedEndDate() {
    	if (endDate == null) {
    		return "";
    	}
    	return endDate.toString("MM/dd/yyyy");
    }

    public void setStartDate(LocalDate startDate) {
    	this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
    	this.endDate = endDate;
    }
    
    public void addCard(Card card){
    	if (this.cards == null) {
    		this.cards = new ArrayList<Card>();
    	}
    	this.cards.add(card);
    }

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public List<Card> getCards() {
	    if (cards == null) {
            cards = new ArrayList<Card>();
        }
	    
		return cards;
	}
	
	public List<Card> getTodoCards() {
		return cardsByStatus(Status.TODO);
	}
	public List<Card> getDoneCards() {
		return cardsByStatus(Status.DONE);
	}

	private List<Card> cardsByStatus(Status status) {
		List<Card> result = new ArrayList<Card>();
		for (Card card : cards) {
			if (status.equals(card.getStatus())) {
				result.add(card);
			}
		}
		return result;
	}
	
	public boolean isCurrent() {
	    if (this.startDate != null && 
	            this.startDate.compareTo(new LocalDate()) <= 0 &&
	            (this.endDate == null || this.endDate.compareTo(new LocalDate()) >= 0)) {
	        return true;
	    } else {
	        return false;
	    }
	}

	public boolean isStartable() {
		return this.startDate == null || this.startDate.isAfter(new LocalDate());
	}
}
