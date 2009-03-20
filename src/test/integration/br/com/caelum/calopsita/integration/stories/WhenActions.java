package br.com.caelum.calopsita.integration.stories;

import org.hibernate.Session;

import br.com.caelum.seleniumdsl.Browser;
import br.com.caelum.seleniumdsl.Form;

public class WhenActions {

    private final Browser browser;
	private String user;
	private final Session session;
	private String storyName;

    public WhenActions(Browser browser, Session session) {
        this.browser = browser;
		this.session = session;
    }

    public void iSignUpAs(String login) {
        iClickOn("Sign Up");
        Form form = browser.currentPage().form("signUp");
        form.field("user.name").type(login);
        form.field("user.login").type(login);
        form.field("user.email").type(login + "@caelum.com.br");
        form.field("user.password").type(login);
        form.field("user.confirmation").type(login);
        form.submit();
    }

    public void iLoginAs(String login) {
        iClickOn("Login");
        Form form = browser.currentPage().form("login");
        form.field("user.login").type(login);
        form.field("user.password").type(login);
        form.submit();
    }

    public void iLogout() {
        iClickOn("Logout");
    }

    public void iClickOn(String link) {
        browser.currentPage().navigate("link=" + link);
    }

    public void iOpenProjectPageDirectly() {
        browser.open("/calopsita/project/form");
    }

    public void iListProjects() {
    	iClickOn("List Projects");
    }

    public void iAddTheProject(String name) {
        iClickOn("New Project");
        Form form = browser.currentPage().form("addProject");
        form.field("project.name").type(name);
        form.field("project.description").type(name);
        form.submit();
    }

	public void iOpenProjectPageOf(String projectName) {
		browser.currentPage().navigate("link=List Projects");
		browser.currentPage().navigate("link=" + projectName);
	}

	public WhenActions iAdd(String user) {
		this.user = user;
		return this;
	}

	public void asColaborator() {
		browser.currentPage().navigate("link=Add Colaborator")
			.form("addColaborator")
				.select("colaborator.login").choose(user)
				.submit();
	}

	public void iDirectlyOpenProjectPageOf(String projectName) {
		Long id = (Long) session.createQuery("select id from Project p where p.name = :name")
			.setParameter("name", projectName).setMaxResults(1).uniqueResult();
		browser.open("/calopsita/project/show/" + id + "/");
	}

	public WhenActions iAddTheStory(String storyName) {
		this.storyName = storyName;
		return this;
	}

	public void withDescription(String description) {
		browser.currentPage().navigate("link=Add Story");
		browser.currentPage()
			.form("addStory")
				.field("story.name").type(storyName)
				.field("story.description").type(description)
				.submit();
	}
}
