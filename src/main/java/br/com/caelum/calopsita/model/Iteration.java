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

import br.com.caelum.calopsita.model.Story.Status;

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
    private List<Story> stories;

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

    public void setStartDate(LocalDate startDate) {
    	this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
    	this.endDate = endDate;
    }
    
    public void addStory(Story story){
    	if (this.stories == null) {
    		this.stories = new ArrayList<Story>();
    	}
    	this.stories.add(story);
    }

	public void setStories(List<Story> stories) {
		this.stories = stories;
	}

	public List<Story> getStories() {
	    if (stories == null) {
            stories = new ArrayList<Story>();
        }
	    
		return stories;
	}
	
	public List<Story> getTodoStories() {
		return storiesByStatus(Status.TODO);
	}
	public List<Story> getDoneStories() {
		return storiesByStatus(Status.DONE);
	}

	private List<Story> storiesByStatus(Status status) {
		List<Story> result = new ArrayList<Story>();
		for (Story story : stories) {
			if (status.equals(story.getStatus())) {
				result.add(story);
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
