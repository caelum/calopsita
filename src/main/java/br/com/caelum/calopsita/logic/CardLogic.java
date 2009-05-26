package br.com.caelum.calopsita.logic;

import java.util.ArrayList;
import java.util.List;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.calopsita.repository.CardRepository;

@Component
@InterceptedBy( { HibernateInterceptor.class, AuthenticationInterceptor.class, AuthorizationInterceptor.class })
public class CardLogic {

	private final CardRepository repository;
	private Project project;
	private final User currentUser;
	private final ProjectRepository projectRepository;
	private List<Card> stories;
	private Card story;

	public CardLogic(User user, CardRepository repository, ProjectRepository projectRepository) {
		this.currentUser = user;
		this.repository = repository;
		this.projectRepository = projectRepository;
	}

	public void save(Card story, Project project) {
		this.project = project;
		story.setProject(project);
		repository.add(story);
		this.stories = this.projectRepository.listStoriesFrom(project);
	}
	
	public void saveSub(Card story) {
		repository.add(story);
		this.stories = this.repository.listSubstories(story.getParent());
		this.story = story.getParent();
		this.project = story.getProject();
	}
	
	public void edit(Card story) {
		this.story = this.repository.load(story);
		this.stories = this.repository.listSubstories(story);
	}

	public Card getStory() {
		return story;
	}
	
	public void update(Card story) {
		Card managedStory = repository.load(story);
		this.project = managedStory.getProject();
		managedStory.setName(story.getName());
		managedStory.setDescription(story.getDescription());
		repository.update(managedStory);
		this.stories = this.projectRepository.listStoriesFrom(project);
	}
	
	public void prioritization(Project project) {
		this.project = this.projectRepository.get(project.getId());
		this.stories = this.projectRepository.listStoriesFrom(project);
	}
	public void prioritize(Project project, List<Card> stories) {
		for (Card story : stories) {
			Card loaded = repository.load(story);
			loaded.setPriority(story.getPriority());
		}
		prioritization(project);
	}
	public List<Card> getStories() {
		return stories;
	}
	
	public List<List<Card>> getGroupedStories() {
		List<List<Card>> result = new ArrayList<List<Card>>();
		if (stories != null) {
			for (int i = maxPriority(stories); i >= 0; i--) {
				result.add(new ArrayList<Card>());
			}
			for (Card story : stories) {
				result.get(story.getPriority()).add(story);
			}
		}
		return result;
	}

	private int maxPriority(List<Card> stories2) {
		int max = 0;
		for (Card story : stories2) {
			if (story.getPriority() > max) {
				max = story.getPriority();
			}
		}
		return max;
	}

	public Project getProject() {
		return project;
	}

	public String delete(Card story, boolean deleteSubstories) {
		Card loaded = repository.load(story);
		this.project = loaded.getProject();
		if (this.project.getColaborators().contains(currentUser) || this.project.getOwner().equals(currentUser)) {
		    this.project = loaded.getProject();
	        if (deleteSubstories) {
	            for (Card sub : loaded.getSubstories()) {
	                repository.remove(sub);
	            }
	        } else {
	            for (Card sub : loaded.getSubstories()) {
	                sub.setParent(null);
	                repository.update(sub);
	            }
	        }
	        repository.remove(loaded);
	        return "ok";
		} else {
			return "invalid";
		}
		
	}
}
