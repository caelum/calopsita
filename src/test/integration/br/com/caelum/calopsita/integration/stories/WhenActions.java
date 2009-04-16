package br.com.caelum.calopsita.integration.stories;

import org.hibernate.Session;

import br.com.caelum.seleniumdsl.Browser;
import br.com.caelum.seleniumdsl.Form;

public class WhenActions {

    private final Browser browser;
	private String user;
	private final Session session;
	private String storyName;
    private String iterationGoal;

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
        Form form = browser.currentPage().form("login");
        form.field("user.login").type(login);
        form.field("user.password").type(login);
        form.submit();
    }

    public void iLogout() {
        iClickOn("Logout");
    }

    public void iClickOn(String link) {
        browser.currentPage().navigateLink(link);
    }

    public void iOpenProjectPageDirectly() {
        browser.open("/calopsita/project/form");
    }

    public void iListProjects() {
    	browser.open("/calopsita/project/list");
    }

    public void iAddTheProject(String name) {
        iClickOn("New Project");
        Form form = browser.currentPage().form("addProject");
        form.field("project.name").type(name);
        form.field("project.description").type(name);
        form.submit();
    }

	public WhenActions iOpenProjectPageOf(String projectName) {
		iListProjects();
		iClickOn(projectName);
		return this;
	}

	public WhenActions iAdd(String user) {
		this.user = user;
		return this;
	}

	public void asColaborator() {
		iClickOn("Add Colaborator");
		browser.currentPage()
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

	public void withDescription(String description) throws InterruptedException {
		iClickOn("Add Story");
		browser.currentPage()
			.form("addStory")
				.field("story.name").type(storyName)
				.field("story.description").type(description)
				.submit();
		browser.currentPage().waitUntil("!($('#stories').is(':empty'))", 1000);
	}

	public WhenActions iEditTheStory(String storyName) {
		iClickOn(storyName);
		return this;
	}

	public void changingDescriptionTo(String storyDescription) {
		browser.currentPage().form("editStory")
							.field("story.description").type(storyDescription)
							.submit();
	}

	public WhenActions iAddTheIteration(String iterationGoal) {
        this.iterationGoal = iterationGoal;
        return this;
    }

    public WhenActions withStartDate(String date) {
    	iClickOn("Add Iteration");
        browser.currentPage()
            .form("addIteration")
                .field("iteration.goal").type(iterationGoal)
                .field("iteration.startDate").type(date);
        return this;
    }

    public void withEndDate(String date) {
        browser.currentPage()
	        .form("addIteration")
	            .field("iteration.endDate").type(date)
	            .submit();
    }

	public WhenActions iOpenThePageOfIterationWithGoal(String goal) {
		iClickOn(goal);
		return this;
	}
	
	public WhenActions and() {
		return this;
	}

	public void inThisIteration() {
	}

}
