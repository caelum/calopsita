package br.com.caelum.calopsita.logic;

import java.util.List;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Story;
import br.com.caelum.calopsita.model.User;
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
    private final User currentUser;

    public IterationLogic(User user, IterationRepository repository, StoryRepository storyRepository) {
        this.currentUser = user;
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
    	this.project = this.iteration.getProject();
    	otherStories = storyRepository.storiesWithoutIteration(project);
    }
    
    public void updateStories(Iteration iteration, List<Story> stories) {
    	for (Story story : stories) {
			Story loaded = storyRepository.load(story);
			loaded.setIteration(iteration);
			loaded.setStatus(story.getStatus());
			storyRepository.update(loaded);
		}
    	this.iteration = iteration;
    	
    }

    public void removeStories(Iteration iteration, List<Story> stories) {
    	for (Story story : stories) {
			Story loaded = storyRepository.load(story);
			loaded.setIteration(null);
			storyRepository.update(loaded);
		}
    	this.iteration = iteration;
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

    public String delete(Iteration iteration) {
        Iteration loaded = repository.load(iteration);
        this.project = loaded.getProject();
        if(this.project.getColaborators().contains(currentUser) || this.project.getOwner().equals(currentUser)) {
            for (Story story : loaded.getStories()) {
                for (Story sub : storyRepository.listSubstories(story)) {
                    sub.setIteration(null);
                    storyRepository.update(sub);
                }
                Story storyLoaded = storyRepository.load(story);
                storyLoaded.setIteration(null);
                storyRepository.update(storyLoaded);
            }
            repository.remove(loaded);
            return "ok";
        } else {
            return "invalid";
        }
    }

}
