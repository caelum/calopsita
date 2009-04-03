package br.com.caelum.calopsita.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
public class Iteration implements Identifiable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Project project;

    private String goal;

    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime startDate;

    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime endDate;

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

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) throws ParseException {
    	if (startDate != null && !startDate.isEmpty()) {
			this.startDate = new DateTime(new SimpleDateFormat("dd/MM/yyyy").parse(startDate));
		} else {
			this.startDate = null;
		}
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) throws ParseException {
    	if (endDate != null && !endDate.isEmpty()) {
			this.endDate = new DateTime(new SimpleDateFormat("dd/MM/yyyy").parse(endDate));
		} else {
			this.endDate = null;
		}
    }
}
