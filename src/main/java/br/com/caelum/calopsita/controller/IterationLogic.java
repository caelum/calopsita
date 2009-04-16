package br.com.caelum.calopsita.controller;

import java.util.List;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Story;
import br.com.caelum.calopsita.repository.IterationRepository;
import br.com.caelum.calopsita.repository.StoryRepository;

@Component
@InterceptedBy( { HibernateInterceptor.class, AuthenticationInterceptor.class, AuthorizationInterceptor.class })
public class IterationLogic {

    private Project project;
    private final IterationRepository repository;
	private Iteration iteration;
	private final StoryRepository storyRepository;
	private List<Story> otherStories;

    public IterationLogic(IterationRepository repository, StoryRepository storyRepository) {
        this.repository = repository;
		this.storyRepository = storyRepository;
    }

    public void save(Iteration iteration, Project project) {
        this.project = project;
        iteration.setProject(project);
        repository.add(iteration);
    }
    
    public void show(Iteration iteration) {
    	this.iteration = repository.load(iteration);
    	otherStories = storyRepository.storiesWithoutIteration();
    }
    
    public Iteration getIteration() {
		return iteration;
	}
    
    public List<Story> getOtherStories() {
		return otherStories;
	}
    
    public Project getProject() {
        return project;
    }
}
