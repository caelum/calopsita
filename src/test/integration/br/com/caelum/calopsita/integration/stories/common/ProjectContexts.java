package br.com.caelum.calopsita.integration.stories.common;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.persistence.dao.UserDao;
import br.com.caelum.seleniumdsl.Browser;

public class ProjectContexts<T extends ProjectContexts<T>> extends GivenContexts {

	private final Session session;
	private final Project project;
	private final Browser browser;

	public ProjectContexts(Project project, Session session, Browser browser) {
		super(browser, session);
		this.project = project;
		this.session = session;
		this.browser = browser;
	}

	@Override
	public GivenContexts and() {
    	return new GivenContexts(browser, session);
    }

	public T ownedBy(String login) {
        User user = new UserDao(session).find(login);
        project.setOwner(user);
        session.save(user);
        session.flush();
        return (T) this;
    }

	public T withColaborator(String login) {
		User user = new UserDao(session).find(login);
        project.getColaborators().add(user);
        session.flush();
        return (T) this;
	}

	public IterationContexts withAnIterationWhichGoalIs(String goal) {
		Iteration iteration = new Iteration();
		iteration.setGoal(goal);
		iteration.setProject(project);
		session.save(iteration);
		session.flush();
		return new IterationContexts(iteration, session, browser);
	}

	public CardContexts<T> withACardNamed(String storyName) {
		Card story = new Card();
		story.setName(storyName);
		story.setProject(project);
		session.save(story);
		session.flush();
		return new CardContexts<T>(story, session, browser, (T) this);
	}

	public T whichDescriptionIs(String description) {
		project.setDescription(description);
		session.flush();
		return (T) this;
	}
}
