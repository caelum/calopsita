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
import br.com.caelum.calopsita.repository.CardRepository;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Hibernate;
import br.com.caelum.vraptor.validator.Validations;
import br.com.caelum.vraptor.view.Results;

@Resource
public class CardsController {

	private final CardRepository repository;
	private final User currentUser;
	private final ProjectRepository projectRepository;
    private final Validator validator;
    private final Result result;

	public CardsController(Result result, Validator validator, SessionUser user, CardRepository repository, ProjectRepository projectRepository) {
		this.result = result;
        this.validator = validator;
        this.currentUser = user.getUser();
		this.repository = repository;
		this.projectRepository = projectRepository;
	}

	@Path("/projects/{project.id}/cards/") @Post
	public void save(final Card card, Project project, List<Gadgets> gadgets) {
		card.setProject(project);
		validator.checking(new Validations() {
            {
                that(Hibernate.validate(card));
            }
        });
		repository.add(card);
		if (gadgets != null) {
			for (Gadgets gadget : gadgets) {
				repository.add(gadget.createGadgetFor(card));
			}
		}
		result.include("project", project);
		result.include("cards", this.projectRepository.listCardsFrom(project));
		result.use(page()).forward("/WEB-INF/jsp/cards/update.jsp");
	}

	@Path("/projects/{project.id}/cards/saveSub/") @Post
	public void saveSub(Card card) {
		repository.add(card);
		result.include("cards", this.repository.listSubcards(card.getParent()));
		result.include("card", card.getParent());
		result.include("project", card.getProject());
		result.use(page()).forward("/WEB-INF/jsp/cards/update.jsp");
	}

	@Path("/cards/{card.id}/") @Get
	public void edit(Card card) {
	    result.include("card", this.repository.load(card));
	    result.include("gadgets", Gadgets.valueOf(this.repository.listGadgets(card)));
	    result.include("cards", this.repository.listSubcards(card));
	}

	@Path("/cards/{card.id}/") @Post
	public void update(Card card, List<Gadgets> gadgets) {
		Card loaded = repository.load(card);
		Project project = loaded.getProject();
		loaded.setName(card.getName());
		loaded.setDescription(card.getDescription());

		repository.updateGadgets(card, gadgets);
		repository.update(loaded);
		result.include("cards", this.projectRepository.listCardsFrom(project));
		result.use(logic()).redirectTo(ProjectsController.class).cards(project);
	}

	@Path("/cards/{card.id}/") @Delete
	public void delete(Card card, boolean deleteSubcards) {
		Card loaded = repository.load(card);
		final Project project = loaded.getProject();

		validator.checking(new Validations() {
			{
				that(currentUser, anyOf(
							isIn(project.getColaborators()),
							is(equalTo(project.getOwner()))));
			}
		});
        if (deleteSubcards) {
            for (Card sub : loaded.getSubcards()) {
                repository.remove(sub);
            }
        } else {
            for (Card sub : loaded.getSubcards()) {
                sub.setParent(null);
                repository.update(sub);
            }
        }
        repository.remove(loaded);
        result.use(Results.nothing());
	}

}
