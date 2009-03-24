package br.com.caelum.calopsita.controller;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Story;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.StoryRepository;

@Component
@InterceptedBy( { HibernateInterceptor.class, AuthenticationInterceptor.class, AuthorizationInterceptor.class })
public class StoryLogic {

	private final StoryRepository repository;
	private Project project;
	private final User currentUser;

	public StoryLogic(User user, StoryRepository repository) {
		this.currentUser = user;
		this.repository = repository;
	}

	public void save(Story story, Project project) {
		this.project = project;
		story.setProject(project);
		story.setOwner(currentUser);
		repository.add(story);
	}

	public void update(Story story, Project project) {
		this.project = project;
		Story managedStory = repository.load(story);
		managedStory.setName(story.getName());
		managedStory.setDescription(story.getDescription());
		repository.update(managedStory);
	}
	
	public Project getProject() {
		return project;
	}
}
