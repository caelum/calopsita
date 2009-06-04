package br.com.caelum.calopsita.integration.stories.common;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Gadgets;
import br.com.caelum.seleniumdsl.Browser;
import br.com.caelum.seleniumdsl.Form;

public class WhenActions {

    private final Browser browser;
	private String user;
	private final Session session;
	private String cardName;
    private String iterationGoal;
	private String linkName;
	private boolean prioritizable;

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
        browser.currentPage().clickLink(link);
    }

    public void iOpenProjectPageDirectly() {
        browser.open("/calopsita/projects/new/");
    }

    public void iListProjects() {
    	browser.open("/calopsita/projects/");
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

	public WhenActions iDirectlyOpenProjectPageOf(String projectName) {
		Long id = (Long) session.createQuery("select id from Project p where p.name = :name")
			.setParameter("name", projectName).setMaxResults(1).uniqueResult();
		browser.open("/calopsita/projects/" + id + "/admin/");
		return this;
	}

	public WhenActions iAddTheCard(String cardName) {
		this.cardName = cardName;
		this.linkName = "Add Card";
		return this;
	}
	public WhenActions iAddTheSubcard(String cardName) {
		this.cardName = cardName;
		this.linkName = "Add Subcard";
		return this;
	}

	public void withDescription(String description) {
		iClickOn(linkName);
		Form form = browser.currentPage() .form("addCard");
		form.field("card.name").type(cardName)
			.field("card.description").type(description);
		if (prioritizable) {
			form.check(Gadgets.PRIORITIZATION.name());
		}

		form.submit();
		iClickOn(linkName);
		browser.currentPage().waitUntil("!($('#cards').is(':empty'))", 1000);
	}

	public WhenActions iEditTheCard(String cardName) {
		iClickOn(cardName);
		return this;
	}

	public void changingDescriptionTo(String cardDescription) {
		browser.currentPage().form("editCard")
							.field("card.description").type(cardDescription)
							.submit();
	}

	public WhenActions iAddTheIteration(String iterationGoal) {
		iClickOn("Add Iteration");
        this.iterationGoal = iterationGoal;
        return this;
    }

    public WhenActions withStartDate(String date) {
        browser.currentPage()
            .form("addIteration")
                .field("iteration.goal").type(iterationGoal)
                .field("iteration.startDate").type(date);
        return this;
    }

    public WhenActions withEndDate(String date) {
        browser.currentPage()
	        .form("addIteration")
	            .field("iteration.endDate").type(date)
	            .submit();
        return this;
    }

	public WhenActions iOpenThePageOfIterationWithGoal(String goal) {
		iClickOn(goal);
		return this;
	}

	public WhenActions and() {
		return this;
	}

	public void iChangeTheUrlToCalopsitasRoot() {
		browser.open("/calopsita/");
	}

	public void inThisIteration() {
		browser.currentPage().dragAndDrop(cardName, "todo_cards");
		browser.currentPage().waitUntil("$('.ui-selected').length == 0", 2000);
	}

	public WhenActions iRemoveTheCard(String name) {
		this.cardName = name;
		return this;
	}

	public void ofThisIteration() {
		browser.currentPage().dragAndDrop(cardName, "backlog_list");
		browser.currentPage().waitUntil("$('.ui-selected').length == 0", 2000);
	}

	public WhenActions iOpenPriorizationPage() {
		iClickOn("Prioritize");
		return this;
	}

	public WhenActions iLowerPriorityOf(String cardName) {
		browser.currentPage().dragAndDrop(cardName, "lowerPriority");
		return this;
	}

	public WhenActions iSaveThePriorization() {
		browser.currentPage().form("prioritizationForm").submit();
		return this;
	}

	public WhenActions iFlagTheCard(String cardName) {
		this.cardName = cardName;
		return this;
	}

	public void asDone() {
		browser.currentPage().dragAndDrop(cardName, "done_cards");
		browser.currentPage().waitUntil("$('#done_cards .card').length > 0", 5000);
	}

	public WhenActions iOpenThePageOfCardNamed(String cardName) {
		iClickOn(cardName);
		return this;
	}

    public WhenActions iDeleteTheIterationWithGoal(String goal) {
        browser.currentPage().click("delete " + goal);
        return this;
    }
	public WhenActions iDeleteTheCard(String cardName) {
		browser.currentPage().click("delete " + cardName);
		return this;
	}

	public WhenActions andConfirm(String operation) {
		browser.currentPage().click("jqi_" + operation + "_buttonYes");
		browser.currentPage().waitUntil("!$('#jqi_state_" + operation + "').is(':visible')", 2000);
		return this;
	}

	public WhenActions andDontConfirm(String operation) {
		browser.currentPage().click("jqi_" + operation + "_buttonNo");
		browser.currentPage().waitUntil("!$('#jqi_state_" + operation + "').is(':visible')", 2000);
		return this;
	}

	public WhenActions iDeleteTheProject(String projectName) {
		iDeleteTheCard(projectName);
		return this;
	}

	public void iStartTheIteration(String iterationGoal) {
		browser.currentPage().click("start " + iterationGoal);
	}
    public void iEndTheIteration(String iterationGoal) {
        browser.currentPage().click("end " + iterationGoal);
    }

	public WhenActions iEditTheIteration() {
		iClickOn("Edit");
		return this;
	}

	public WhenActions withGoal(String goal) {
		this.iterationGoal = goal;
		return this;
	}

	public void iChangeDescriptionTo(String description) {
		iClickOn("Edit");
		browser.currentPage().form("projectForm")
			.field("project.description").type(description)
			.submit();
	}

    public WhenActions iOpenAdminPage() {
        iClickOn("Admin");
        return this;
    }

    public WhenActions iOpenIterationsPage() {
        iClickOn("Iterations");
        return this;
    }

    public WhenActions iOpenCardsPage() {
        iClickOn("Cards");
        return this;
    }

	public WhenActions prioritizable() {
		this.prioritizable = true;
		return this;
	}

	public void addingGadget(Gadgets gadget) {
		browser.currentPage().form("editCard")
			.check(gadget.name())
			.submit();
	}

	public void removingGadget(Gadgets gadget) {
		browser.currentPage().form("editCard")
			.uncheck(gadget.name())
			.submit();
	}

	public void andWait() {
		browser.waitForPageLoad(2000);
	}
}
