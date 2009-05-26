package br.com.caelum.calopsita.logic;

import java.util.List;

import org.joda.time.LocalDate;
import org.vraptor.annotations.Component;
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
import br.com.caelum.calopsita.repository.ProjectRepository;

@Component
@InterceptedBy( { HibernateInterceptor.class, AuthenticationInterceptor.class, AuthorizationInterceptor.class })
public class IterationLogic {

    private Project project;
    private final IterationRepository repository;
	private Iteration iteration;
	private final CardRepository cardRepository;
	private List<Card> otherStories;
    private final User currentUser;
    private final ProjectRepository projectRepository;
    private List<Iteration> iterations;

    public IterationLogic(User user, IterationRepository repository, CardRepository cardRepository, ProjectRepository projectRepository) {
        this.currentUser = user;
        this.repository = repository;
		this.cardRepository = cardRepository;
        this.projectRepository = projectRepository;
    }

    public void save(Iteration iteration) {
        this.project = iteration.getProject();
        validateDate(iteration);
        repository.add(iteration);
    }

	private void validateDate(Iteration iteration) {
		if (iteration.getStartDate() != null && iteration.getEndDate() != null &&
        		iteration.getStartDate().compareTo(iteration.getEndDate()) > 0) {
			throw new IllegalArgumentException("iteration start date is greater than end date");
		}
	}

    public void show(Iteration iteration) {
    	this.iteration = repository.load(iteration);
    	this.project = this.iteration.getProject();
    	otherStories = cardRepository.storiesWithoutIteration(project);
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

    public void updateStories(Iteration iteration, List<Card> cards) {
    	for (Card card : cards) {
			Card loaded = cardRepository.load(card);
			loaded.setIteration(iteration);
			loaded.setStatus(card.getStatus());
			cardRepository.update(loaded);
		}
    	this.iteration = iteration;
    }

    public void removeStories(Iteration iteration, List<Card> cards) {
    	for (Card card : cards) {
			Card loaded = cardRepository.load(card);
			loaded.setIteration(null);
			cardRepository.update(loaded);
		}
    	this.iteration = iteration;
    }
    public Iteration getIteration() {
		return iteration;
	}

    public List<Card> getOtherStories() {
		return otherStories;
	}

    public Project getProject() {
        return project;
    }

    public String delete(Iteration iteration) {
        Iteration loaded = repository.load(iteration);
        this.project = loaded.getProject();
        if(this.project.getColaborators().contains(currentUser) || this.project.getOwner().equals(currentUser)) {
            for (Card cards : loaded.getCards()) {
                Card cardLoaded = cardRepository.load(cards);
                cardLoaded.setIteration(null);
                cardRepository.update(cardLoaded);
            }
            repository.remove(loaded);
            return "ok";
        } else {
            return "invalid";
        }
    }

	public void start(Iteration iteration) {
		Iteration loaded = repository.load(iteration);
		if (loaded.isCurrent()) {
			throw new IllegalArgumentException("Tried to start an already started iteration");
		}
		loaded.setStartDate(new LocalDate());
		this.project = loaded.getProject();
	}

    public void end(Iteration iteration) {
        Iteration loaded = repository.load(iteration);
        if (!loaded.isCurrent()) {
            throw new IllegalArgumentException("Tried to end an iteration that has not been started");
        }
        loaded.setEndDate(new LocalDate());
        this.project = loaded.getProject();
    }
	public void update(Iteration iteration) {
		validateDate(iteration);
		Iteration loaded = repository.load(iteration);
		loaded.setGoal(iteration.getGoal());
		loaded.setStartDate(iteration.getStartDate());
		loaded.setEndDate(iteration.getEndDate());
		this.iteration = loaded;
	}

}
