package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.logic;
import static br.com.caelum.vraptor.view.Results.page;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import java.util.List;

import org.joda.time.LocalDate;

import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Iteration;
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
public class IterationsController {

    private final User currentUser;
	private final Result result;
    private final Validator validator;

    public IterationsController(Result result, Validator validator, SessionUser user) {
        this.result = result;
		this.validator = validator;
		this.currentUser = user.getUser();
    }

    @Path("/projects/{iteration.project.id}/iterations/{iteration.id}/edit/") @Get
    public void edit(Iteration iteration) {
		this.result.include("project", iteration.getProject().load());
        this.result.include("iteration", iteration.load());
        this.result.include("today", new LocalDate());
    }

	@Path("/projects/{iteration.project.id}/iterations/") @Post
	public void save(Iteration iteration) {
		validateDate(iteration);
		validator.onErrorUse(page()).of(IterationsController.class).form(iteration.getProject());
		iteration.save();
		result.use(logic()).redirectTo(IterationsController.class).list(iteration.getProject());
	}

    @Path("/projects/{project.id}/iterations/new/") @Get
	public void form(Project project) {
    	this.result.include("project", project.load());
    	this.result.include("today", new LocalDate());
    }

	private void validateDate(final Iteration iteration) {
		validator.checking(new Validations() {
            {
                if (iteration.getStartDate() != null && iteration.getEndDate() != null) {
                	that(iteration.getStartDate(), is(lessThanOrEqualTo(iteration.getEndDate())));
                	that(iteration.getEndDate(), is(greaterThanOrEqualTo(iteration.getStartDate())));
                    and(Hibernate.validate(iteration));
                }
            }
        });
	}

	@Path("/projects/{project.id}/iterations/current/") @Get
    public void current(Project project) {
		this.result.include("project", project.load());
        this.result.include("iteration", project.getCurrentIteration());
        this.result.include("today", new LocalDate());
    }

	@Path("/projects/{project.id}/iterations/") @Get
    public void list(Project project) {
        this.result.include("project", project.load());
        this.result.include("iterations", project.getIterations());
    }

	@Path("/projects/{iteration.project.id}/iterations/{iteration.id}/") @Get
    public void show(Iteration iteration) {
    	result.include("iteration", iteration.load());
    	result.include("project", iteration.getProject().load());
    	result.include("otherCards", iteration.getProject().getCardsWithoutIteration());
    	result.include("today", new LocalDate());
    }

	@Path("/projects/{iteration.project.id}/iterations/{iteration.id}/cards/") @Post
    public void updateCards(Iteration iteration, List<Card> cards) {
    	for (Card card : cards) {
			Card loaded = card.load();
			loaded.setIteration(iteration);
			loaded.setStatus(card.getStatus());
			loaded.update();
		}
    	show(iteration);
    	result.use(page()).forward("/WEB-INF/jsp/iterations/cards.jsp");
    }

	@Path("/projects/{iteration.project.id}/iterations/{iteration.id}/cards/") @Delete
    public void removeCards(Iteration iteration, List<Card> cards) {
    	for (Card card : cards) {
			Card loaded = card.load();
			loaded.setIteration(null);
			loaded.update();
		}
    	show(iteration);
    	result.use(page()).forward("/WEB-INF/jsp/iterations/cards.jsp");
    }

    @Path("/projects/{iteration.project.id}/iterations/{iteration.id}/") @Delete
    public void delete(Iteration iteration) {
        Iteration loaded = iteration.load();
        final Project project = loaded.getProject();

        validator.checking(new Validations() {
			{
				that(currentUser, anyOf(
							isIn(project.getColaborators()),
							is(equalTo(project.getOwner()))));
			}
		});
        validator.onErrorUse(page()).of(IterationsController.class).list(project);
        for (Card card : loaded.getCards()) {
            card.setIteration(null);
        }
        loaded.delete();

        result.use(logic()).redirectTo(IterationsController.class).list(iteration.getProject());
    }

    @Path("/projects/{iteration.project.id}/iterations/{iteration.id}/start/") @Get
	public void start(Iteration iteration) {
		Iteration loaded = iteration.load();
		if (loaded.isCurrent()) {
			throw new IllegalArgumentException("Tried to start an already started iteration");
		}
		loaded.setStartDate(new LocalDate());
		result.use(logic()).redirectTo(IterationsController.class).list(iteration.getProject());
	}

    @Path("/projects/{iteration.project.id}/iterations/{iteration.id}/end/") @Get
    public void end(Iteration iteration) {
        Iteration loaded = iteration.load();
        if (!loaded.isCurrent()) {
            throw new IllegalArgumentException("Tried to end an iteration that has not been started");
        }
        loaded.setEndDate(new LocalDate());
        result.use(logic()).redirectTo(IterationsController.class).list(iteration.getProject());
    }

    @Path("/projects/{iteration.project.id}/iterations/{iteration.id}/") @Post
    public void update(Iteration iteration) {
		validateDate(iteration);
		validator.onErrorUse(page()).of(IterationsController.class).edit(iteration);
		Iteration loaded = iteration.load();
		loaded.setGoal(iteration.getGoal());
		loaded.setStartDate(iteration.getStartDate());
		loaded.setEndDate(iteration.getEndDate());
		result.use(logic()).redirectTo(IterationsController.class).show(iteration);
	}

}
