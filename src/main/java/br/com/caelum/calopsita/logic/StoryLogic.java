package br.com.caelum.calopsita.logic;

import java.util.ArrayList;
import java.util.List;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Story;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.calopsita.repository.StoryRepository;

@Component
@InterceptedBy( { HibernateInterceptor.class, AuthenticationInterceptor.class, AuthorizationInterceptor.class })
public class StoryLogic {

	private final StoryRepository repository;
	private Project project;
	private final User currentUser;
	private final ProjectRepository projectRepository;
	private List<Story> stories;
	private Story story;

	public StoryLogic(User user, StoryRepository repository, ProjectRepository projectRepository) {
		this.currentUser = user;
		this.repository = repository;
		this.projectRepository = projectRepository;
	}

	public void save(Story story, Project project) {
		this.project = project;
		story.setProject(project);
		repository.add(story);
		this.stories = this.projectRepository.listStoriesFrom(project);
	}
	
	public void saveSub(Story story) {
		repository.add(story);
		this.stories = this.repository.listSubstories(story.getParent());
		this.story = story.getParent();
		this.project = story.getProject();
	}
	
	public void edit(Story story) {
		this.story = this.repository.load(story);
		this.stories = this.repository.listSubstories(story);
	}

	public Story getStory() {
		return story;
	}
	
	public void update(Story story) {
		Story managedStory = repository.load(story);
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
	public void prioritize(Project project, List<Story> stories) {
		for (Story story : stories) {
			Story loaded = repository.load(story);
			loaded.setPriority(story.getPriority());
		}
		prioritization(project);
	}
	public List<Story> getStories() {
		return stories;
	}
	
	public List<List<Story>> getGroupedStories() {
		List<List<Story>> result = new ArrayList<List<Story>>();
		if (stories != null) {
			for (int i = maxPriority(stories); i >= 0; i--) {
				result.add(new ArrayList<Story>());
			}
			for (Story story : stories) {
				result.get(story.getPriority()).add(story);
			}
		}
		return result;
	}

	private int maxPriority(List<Story> stories2) {
		int max = 0;
		for (Story story : stories2) {
			if (story.getPriority() > max) {
				max = story.getPriority();
			}
		}
		return max;
	}

	public Project getProject() {
		return project;
	}

	public String delete(Story story, boolean deleteSubstories) {
		Story loaded = repository.load(story);
		this.project = loaded.getProject();
		if (this.project.getColaborators().contains(currentUser) || this.project.getOwner().equals(currentUser)) {
		    this.project = loaded.getProject();
	        if (deleteSubstories) {
	            for (Story sub : loaded.getSubstories()) {
	                repository.remove(sub);
	            }
	        } else {
	            for (Story sub : loaded.getSubstories()) {
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
