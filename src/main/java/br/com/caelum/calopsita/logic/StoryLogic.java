package br.com.caelum.calopsita.logic;

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

	public StoryLogic(User user, StoryRepository repository, ProjectRepository projectRepository) {
		this.currentUser = user;
		this.repository = repository;
		this.projectRepository = projectRepository;
	}

	public void save(Story story, Project project) {
		this.project = project;
		story.setProject(project);
		story.setOwner(currentUser);
		repository.add(story);
		this.stories = this.projectRepository.listStoriesFrom(project);
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
	
	public Project getProject() {
		return project;
	}
}
