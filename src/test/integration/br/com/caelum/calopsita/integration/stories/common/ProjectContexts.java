package br.com.caelum.calopsita.integration.stories.common;

import org.hibernate.Session;
import org.joda.time.LocalDate;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Story;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.persistence.dao.UserDao;
import br.com.caelum.seleniumdsl.Browser;

public class ProjectContexts {

	private final Browser browser;
	private final GivenContexts parent;
	private final Session session;
	private final Project project;
	private Story story;
	private String storyName;
	private Iteration iteration;

	public ProjectContexts(Project project, GivenContexts parent, Session session, Browser browser) {
		this.project = project;
		this.parent = parent;
		this.session = session;
		this.browser = browser;
	}

	public GivenContexts and() {
    	return parent;
    }

	public ProjectContexts ownedBy(String login) {
        UserDao userDao = new UserDao(session);
        User user = userDao.find(login);
        project.setOwner(user);
        session.save(user);
        session.flush();
        return this;
    }

	public ProjectContexts withColaborator(String login) {
		UserDao userDao = new UserDao(session);
        User user = userDao.find(login);
        project.getColaborators().add(user);
        session.flush();
        return this;
	}

	public ProjectContexts whichDescriptionIs(String storyDescription) {
		Story oldstory = story;
		story = new Story();
		story.setName(storyName);
		story.setDescription(storyDescription);
		story.setProject(project);
		story.setParent(oldstory);
		session.save(story);
		session.flush();
		return this;
	}

	public ProjectContexts withAnIterationWhichGoalIs(String goal) {
		iteration = new Iteration();
		iteration.setGoal(goal);
		iteration.setProject(project);
		session.save(iteration);
		session.flush();
		return this;
	}

	public ProjectContexts withAStoryNamed(String storyName) {
		this.storyName = storyName;
		return this;
	}

	public ProjectContexts withPriority(int priority) {
		story.setPriority(priority);
		session.saveOrUpdate(story);
		session.flush();
		return this;
	}

	public ProjectContexts insideThisIteration() {
		story.setIteration(iteration);
		session.saveOrUpdate(story);
		session.flush();
		return this;
	}

	public ProjectContexts withASubstoryNamed(String storyName) {
		this.storyName = storyName;
		return this;
	}

    public ProjectContexts startingYesterday() {
        this.iteration.setStartDate(new LocalDate().minusDays(1));
        session.saveOrUpdate(iteration);
        session.flush();
        return this;
    }
	public ProjectContexts startingAt(LocalDate date) {
		iteration.setStartDate(date);
		session.flush();
		return this;
	}

	public ProjectContexts endingAt(LocalDate date) {
		iteration.setEndDate(date);
		session.flush();
		return this;
	}

}
