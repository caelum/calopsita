package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.logic;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.joda.time.LocalDate;
import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.CardRepository;
import br.com.caelum.calopsita.repository.IterationRepository;
import br.com.caelum.calopsita.repository.StoryRepository;
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
@InterceptedBy( { HibernateInterceptor.class, AuthenticationInterceptor.class, AuthorizationInterceptor.class })
public class IterationsController {

    private final IterationRepository repository;
	private final CardRepository cardRepository;
    private final User currentUser;
    private final Result result;
    private final Validator validator;

    public IterationsController(Validator validator, Result result, User user, IterationRepository repository, StoryRepository storyRepository) {
        this.validator = validator;
        this.result = result;
        this.currentUser = user;
        this.repository = repository;
		this.cardRepository = cardRepository;
        this.projectRepository = projectRepository;
    }

    @Path("/iterations/") @Post
    public void save(final Iteration iteration) {
        validateDate(iteration);
        repository.add(iteration);
        result.use(logic()).redirectTo(ProjectsController.class).show(iteration.getProject());
    }

	private void validateDate(final Iteration iteration) {
		validator.checking(new Validations() {
            {
                that(iteration.getStartDate()).shouldBe(notNullValue());
                that(iteration.getEndDate()).shouldBe(notNullValue());
                that(iteration.getStartDate()).shouldBe(greaterThan(iteration.getEndDate()));
                and(Hibernate.validate(iteration));
            }
        });
	}

    public void show(Iteration iteration) {
    	this.iteration = repository.load(iteration);
    	this.project = this.iteration.getProject();
    	otherCards = cardRepository.cardsWithoutIteration(project);
    }

    public void current(Project project) {
        this.project = this.projectRepository.get(project.getId());
        this.iteration = this.repository.getCurrentIterationFromProject(project);
    }

    public void list(Project project) {
        this.project = this.projectRepository.get(project.getId());
        this.iterations = this.projectRepository.listIterationsFrom(project);
    }

	@Path("/iterations/{iteration.id}") @Get
    public void show(Iteration iteration) {
    	Iteration loaded = repository.load(iteration);
    	Project project = loaded.getProject();
    	result.include("iteration", loaded);
    	result.include("project", project);
    	result.include("otherStories", storyRepository.storiesWithoutIteration(project));
    }
    
	public void current(Project project) {
        this.project = this.projectRepository.get(project.getId());
        this.iteration = this.repository.getCurrentIterationFromProject(project);
    }

    public void list(Project project) {
        this.project = this.projectRepository.get(project.getId());
        this.iterations = this.projectRepository.listIterationsFrom(project);
    }

    public List<Iteration> getIterations() {
        return iterations;
    }
	@Path("/iterations/{iteration.id}/updateCards/") @Post
    public void updateCards(Iteration iteration, List<Card> cards) {
    	for (Card card : cards) {
			Card loaded = cardRepository.load(card);
			loaded.setIteration(iteration);
			loaded.setStatus(card.getStatus());
			cardRepository.update(loaded);
		}
    	result.use(logic()).redirectTo(IterationsController.class).show(iteration);
    }

	@Path("/iterations/{iteration.id}/removeCards/") @Post
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
        this.project = loaded.getProject();
        if(this.project.getColaborators().contains(currentUser) || this.project.getOwner().equals(currentUser)) {
            for (Card cards : loaded.getCards()) {
                Card cardLoaded = cardRepository.load(cards);
                cardLoaded.setIteration(null);
                cardRepository.update(cardLoaded);
            }
            repository.remove(loaded);
            result.use(logic()).redirectTo(ProjectsController.class).show(project);
        }
    }

    @Path("/iterations/{iteration.id}/start/") @Post
	public void start(Iteration iteration) {
		Iteration loaded = repository.load(iteration);
		if (loaded.isCurrent()) {
			throw new IllegalArgumentException("Tried to start an already started iteration");
		}
		loaded.setStartDate(new LocalDate());
		Project project = loaded.getProject();
		result.use(logic()).redirectTo(ProjectsController.class).show(project);
	}

    @Path("/iterations/{iteration.id}/end/") @Post
    public void end(Iteration iteration) {
        Iteration loaded = repository.load(iteration);
        if (!loaded.isCurrent()) {
            throw new IllegalArgumentException("Tried to end an iteration that has not been started");
        }
        loaded.setEndDate(new LocalDate());
        Project project = loaded.getProject();
        result.use(logic()).redirectTo(ProjectsController.class).show(project);
    }
    
    @Path("/iterations/{iteration.id}/") @Post
    public Iteration update(Iteration iteration) {
		validateDate(iteration);
		Iteration loaded = repository.load(iteration);
		loaded.setGoal(iteration.getGoal());
		loaded.setStartDate(iteration.getStartDate());
		loaded.setEndDate(iteration.getEndDate());
		return loaded;
	}

}
