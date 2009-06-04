package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.logic;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;

import org.joda.time.LocalDate;
import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.CardRepository;
import br.com.caelum.calopsita.repository.IterationRepository;
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

@Resource
@InterceptedBy( { AuthorizationInterceptor.class })
public class IterationsController {

    private final IterationRepository repository;
	private final CardRepository cardRepository;
    private final User currentUser;
	private final Result result;
    private final Validator validator;
	private final ProjectRepository projectRepository;

    public IterationsController(Result result, Validator validator, SessionUser user, IterationRepository repository, CardRepository cardRepository, ProjectRepository projectRepository) {
        this.result = result;
		this.validator = validator;
		this.currentUser = user == null? null : user.getUser();
        this.repository = repository;
		this.cardRepository = cardRepository;
        this.projectRepository = projectRepository;
    }

    @Path("/iterations/") @Post
    public void save(final Iteration iteration) {
        validateDate(iteration);
        repository.add(iteration);
        result.use(logic()).redirectTo(IterationsController.class).list(iteration.getProject());
    }

	private void validateDate(final Iteration iteration) {
		validator.checking(new Validations() {
            {
            	that(iteration.getStartDate(), anyOf(is(lessThanOrEqualTo(iteration.getEndDate())), is(nullValue())));
            	that(iteration.getEndDate(), anyOf(is(greaterThanOrEqualTo(iteration.getStartDate())), is(nullValue())));
                and(Hibernate.validate(iteration));
            }
        });
	}

	@Path("/iterations/{project.id}/current/") @Get
    public void current(Project project) {
		this.result.include("project", this.projectRepository.get(project.getId()));
        this.result.include("iteration", this.repository.getCurrentIterationFromProject(project));
    }

	@Path("/iterations/{project.id}/list/") @Get
    public void list(Project project) {
        this.result.include("project", this.projectRepository.get(project.getId()));
        this.result.include("iterations", this.projectRepository.listIterationsFrom(project));
    }

	@Path("/iterations/{iteration.id}/") @Get
    public void show(Iteration iteration) {
    	Iteration loaded = repository.load(iteration);
    	Project project = loaded.getProject();
    	cardRepository.orderCardsByPriority(loaded);
    	result.include("iteration", loaded);
    	result.include("project", project);
    	result.include("otherCards", cardRepository.cardsWithoutIteration(project));
    }

	@Path("/iterations/{iteration.id}/cards/") @Post
    public void updateCards(Iteration iteration, List<Card> cards) {
    	for (Card card : cards) {
			Card loaded = cardRepository.load(card);
			loaded.setIteration(iteration);
			loaded.setStatus(card.getStatus());
			cardRepository.update(loaded);
		}
    	result.use(logic()).redirectTo(IterationsController.class).show(iteration);
    }

	@Path("/iterations/{iteration.id}/cards/") @Delete
    public void removeCards(Iteration iteration, List<Card> cards) {
    	for (Card card : cards) {
			Card loaded = cardRepository.load(card);
			loaded.setIteration(null);
			cardRepository.update(loaded);
		}
    	result.use(logic()).redirectTo(IterationsController.class).show(iteration);
    }

    @Path("/iterations/{iteration.id}/") @Delete
    public void delete(Iteration iteration) {
        Iteration loaded = repository.load(iteration);
        final Project project = loaded.getProject();

        validator.checking(new Validations() {
			{
				that(currentUser, anyOf(
							isIn(project.getColaborators()),
							is(equalTo(project.getOwner()))));
			}
		});
        for (Card cards : loaded.getCards()) {
            Card cardLoaded = cardRepository.load(cards);
            cardLoaded.setIteration(null);
            cardRepository.update(cardLoaded);
        }
        repository.remove(loaded);
        result.use(logic()).redirectTo(IterationsController.class).list(project);
    }

    @Path("/iterations/{iteration.id}/start/") @Get
	public void start(Iteration iteration) {
		Iteration loaded = repository.load(iteration);
		if (loaded.isCurrent()) {
			throw new IllegalArgumentException("Tried to start an already started iteration");
		}
		loaded.setStartDate(new LocalDate());
		Project project = loaded.getProject();
		result.use(logic()).redirectTo(IterationsController.class).list(project);
	}

    @Path("/iterations/{iteration.id}/end/") @Get
    public void end(Iteration iteration) {
        Iteration loaded = repository.load(iteration);
        if (!loaded.isCurrent()) {
            throw new IllegalArgumentException("Tried to end an iteration that has not been started");
        }
        loaded.setEndDate(new LocalDate());
        Project project = loaded.getProject();
        result.use(logic()).redirectTo(IterationsController.class).list(project);
    }

    @Path("/iterations/{iteration.id}/") @Post
    public Iteration update(Iteration iteration) {
		validateDate(iteration);
		Iteration loaded = repository.load(iteration);
		loaded.setGoal(iteration.getGoal());
		loaded.setStartDate(iteration.getStartDate());
		loaded.setEndDate(iteration.getEndDate());
		result.use(logic()).redirectTo(IterationsController.class).show(iteration);
		return loaded;
	}

}
