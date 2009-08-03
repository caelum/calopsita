package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.logic;
import static br.com.caelum.vraptor.view.Results.page;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isIn;

import java.util.List;

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

	private static final String UPDATE_JSP = "/WEB-INF/jsp/cards/update.jsp";
	private final User currentUser;
    private final Validator validator;
    private final Result result;

	public CardsController(Result result, Validator validator, SessionUser user) {
		this.result = result;
        this.validator = validator;
        this.currentUser = user == null? null: user.getUser();
	}

	@Path("/projects/{project.id}/cards/") @Get
    public void list(Project project) {
    	this.result.include("project", project.load());
    	this.result.include("cards",  project.getCards());
    	this.result.include("gadgets", Gadgets.values());
    	this.result.include("cardTypes", project.getCardTypes());
    }

	@Path("/projects/{card.project.id}/cards/") @Post
	public void save(final Card card, List<Gadgets> gadgets) {
		validator.checking(new Validations() {{
            that(Hibernate.validate(card));
        }});
		card.save();
		if (gadgets != null) {
			card.addGadgets(gadgets);
		}
		result.include("project", card.getProject());
		result.include("cards", card.getProject().getCards());
		result.use(page()).forward(UPDATE_JSP);
	}

	@Path("/projects/{card.project.id}/cards/{card.parent.id}/subcards/") @Post
	public void saveSub(Card card) {
		card.save();
		result.include("cards", card.getParent().getSubcards());
		result.include("card", card.getParent());
		result.include("project", card.getProject());
		result.use(page()).forward(UPDATE_JSP);
	}

	@Path("/projects/{card.project.id}/cards/{card.id}/") @Get
	public void edit(Card card) {
	    Card loaded = card.load();
		result.include("card", loaded);
		result.include("project", loaded.getProject());
		result.include("gadgets", Gadgets.values());
	    result.include("cardGadgets", Gadgets.valueOf(card.getGadgets()));
	    result.include("cards", card.getSubcards());
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
        if (deleteSubcards) {
            loaded.deleteSubCards();
        } else {
        	loaded.detachSubCards();
        }
        loaded.delete();
        result.include("cards", project.getCards());
        result.include("project", project);
        result.use(page()).forward(UPDATE_JSP);
	}

}
