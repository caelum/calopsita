package br.com.caelum.calopsita.integration.stories.common;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Story;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.persistence.dao.UserDao;
import br.com.caelum.seleniumdsl.Browser;
import br.com.caelum.seleniumdsl.Form;
import br.com.caelum.seleniumdsl.Page;

public class GivenContexts {

    private final Browser browser;
    private final Session session;
    private Project project;
	private String storyName;
	private Story story;
	private Iteration iteration;

    public GivenContexts(Browser browser, Session session) {
        this.browser = browser;
        this.session = session;
    }

    public void iAmOnTheRootPage() {
        browser.open("/calopsita");
    }

    public GivenContexts thereIsAnUserNamed(String login) {
        User user = new User();
        user.setLogin(login);
        user.setEmail(login + "@caelum.com.br");
        user.setName(login);
        user.setPassword(login);
        session.saveOrUpdate(user);
        session.flush();
        return this;
    }

    public GivenContexts and() {
    	return this;
    }
    public void iAmLoggedInAs(String login) {
        iAmOnTheRootPage();
        Page currentPage = browser.currentPage();
        Form form = currentPage.form("login");
        form.field("user.login").type(login);
        form.field("user.password").type(login);
        form.submit();
        
		assertThat(browser.currentPage().div("user").innerHTML(), containsString(login));
    }

    public void iAmNotLogged() {
        iAmOnTheRootPage();
        if (browser.currentPage().div("user").contains("Logout")) {
            browser.currentPage().click("link=Logout");
        }
    }

    public GivenContexts thereIsAProjectNamed(String name) {
        project = new Project();
        project.setId(1L);
        project.setDescription(name);
        project.setName(name);
        session.save(project);
        session.flush();
        return this;
    }

    public GivenContexts ownedBy(String login) {
        UserDao userDao = new UserDao(session);
        User user = userDao.find(login);
        project.setOwner(user);
        session.save(user);
        session.flush();
        return this;
    }

	public void theUserDoesntExist(String name) {
		session.createQuery("delete from User u where u.name = :name").setParameter("name", name).executeUpdate();
	}

	public GivenContexts withColaborator(String login) {
		UserDao userDao = new UserDao(session);
        User user = userDao.find(login);
        project.getColaborators().add(user);
        session.flush();
        return this;
	}

	public GivenContexts withAStoryNamed(String storyName) {
		this.storyName = storyName;
		return this;
	}

	public GivenContexts whichDescriptionIs(String storyDescription) {
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

	public GivenContexts withAnIterationWhichGoalIs(String goal) {
		iteration = new Iteration();
		iteration.setGoal(goal);
		iteration.setProject(project);
		session.save(iteration);
		session.flush();
		return this;
	}

	public GivenContexts withPriority(int priority) {
		story.setPriority(priority);
		session.saveOrUpdate(story);
		session.flush();
		return this;
	}

	public GivenContexts insideThisIteration() {
		story.setIteration(iteration);
		session.saveOrUpdate(story);
		session.flush();
		return this;
	}

	public GivenContexts withASubstoryNamed(String storyName) {
		this.storyName = storyName;
		return this;
	}

    public GivenContexts startingYesterday() {
        this.iteration.setEndDate(storyName);
        session.saveOrUpdate(iteration);
        session.flush();
        return this;
    }

}
