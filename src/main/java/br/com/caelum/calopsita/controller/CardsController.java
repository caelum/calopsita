package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.http;
import static br.com.caelum.vraptor.view.Results.logic;
import static br.com.caelum.vraptor.view.Results.page;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Gadgets;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Hibernate;
import br.com.caelum.vraptor.validator.Validations;

@Resource
public class CardsController {

	private final User currentUser;
    private final Validator validator;
    private final Result result;

	public CardsController(Result result, Validator validator, SessionUser user) {
		this.result = result;
        this.validator = validator;
        this.currentUser = user.getUser();
	}

	@Path("/projects/{project.id}/cards/") @Get
    public void list(Project project) {
		this.result.include("project", project.load());
    	this.result.include("cards",  project.getToDoCards());
    }

	@Path("/projects/{project.id}/cards/all/") @Get
	public void all(Project project) {
		this.result.include("project", project.load());
		this.result.include("cards",  project.getAllRootCards());
		this.result.use(page()).of(CardsController.class).list(project);
	}

	@Path({	"/projects/{card.project.id}/cards/",
			"/projects/{card.project.id}/cards/{card.parent.id}/subcards/"})
	@Post
	public void save(final Card card, List<Gadgets> gadgets) {
		validator.checking(new Validations() {{
            and(Hibernate.validate(card));
        }});
		validator.onErrorUse(logic()).forwardTo(CardsController.class).form(card.getProject());

		card.setCreator(currentUser);
		card.save();
		if (gadgets != null) {
			card.addGadgets(gadgets);
		}
		result.include("project", card.getProject());
	}

	@Path("/projects/{project.id}/cards/new/") @Get
    public void form(Project project) {
    	this.result.include("project", project.load());
    	this.result.include("gadgets", Gadgets.values());
    	this.result.include("cardTypes", project.getCardTypes());
    }

	@Path("/projects/{card.project.id}/cards/{card.id}/subcards/") @Get
	public void listSubcards(Card card) {
		result.include("card", card.load());
		result.include("cards", card.getSubcards());
		result.include("project", card.getProject().load());
	}

	@Path("/projects/{card.project.id}/cards/{card.id}/subcards/new/") @Get
	public void subcardForm(Card card) {
		result.include("project", card.getProject().load());
		result.include("card", card);
		result.include("parent", card.load());
		result.include("cardTypes", card.getProject().getCardTypes());
		result.include("gadgets", Gadgets.values());
	}

	@Path("/projects/{card.project.id}/cards/{card.id}/") @Get
	public void edit(Card card) {
	    result.include("card", card.load());
		result.include("project", card.getProject().load());
		result.include("gadgets", Gadgets.values());
	    result.include("cardGadgets", Gadgets.valueOf(card.getGadgets()));
	    result.include("cardTypes", card.getProject().getCardTypes());
	}

	@Path("/projects/{card.project.id}/cards/{card.id}/") @Post
	public void update(Card card, List<Gadgets> gadgets) {
		Card loaded = card.load();

		loaded.setName(card.getName());
		loaded.setDescription(card.getDescription());

		Project project = loaded.getProject();
		loaded.updateGadgets(gadgets);
		loaded.update();
		result.use(logic()).redirectTo(CardsController.class).list(project);
	}

	@Path("/projects/{card.project.id}/cards/{card.id}/") @Delete
	public void delete(Card card, boolean deleteSubcards) {
		Card loaded = card.load();
		final Project project = card.getProject().load();

		validator.checking(new Validations() {{
			that(currentUser, anyOf(
						isIn(project.getColaborators()),
						is(equalTo(project.getOwner()))));
		}});
		validator.onErrorUse(http()).sendError(HttpServletResponse.SC_FORBIDDEN);
		List<Long> deleted = new ArrayList<Long>();
		deleted.add(card.getId());
        if (deleteSubcards) {
            deleted.addAll(loaded.deleteSubCards());
        } else {
        	loaded.detachSubCards();
        }

        loaded.delete();
        result.include("deleted", deleted);
	}

}
