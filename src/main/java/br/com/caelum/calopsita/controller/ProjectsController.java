package br.com.caelum.calopsita.controller;

import java.util.List;

import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.calopsita.repository.UserRepository;
import br.com.caelum.vraptor.Resource;

@Resource
@InterceptedBy( { HibernateInterceptor.class, AuthenticationInterceptor.class, AuthorizationInterceptor.class })
public class ProjectsController {

    private final ProjectRepository repository;
    private List<Project> projects;
    private final User currentUser;
	private Project project;
	private final UserRepository userRepository;
	private List<User> users;
	private List<Card> cards;

    public ProjectsController(ProjectRepository repository, UserRepository userRepository, User user) {
        this.repository = repository;
		this.userRepository = userRepository;
        this.currentUser = user;
    }

    public void form() {

    }
    
    public void admin(Project project) {
        this.project = this.repository.get(project.getId());
        this.users = this.userRepository.listUnrelatedUsers(this.project);
    }

    public void cards(Project project) {
        this.project = this.repository.get(project.getId());
        this.cards = this.repository.listCardsFrom(project);
    }
    
    public void save(Project project) {
        project.setOwner(currentUser);
        this.repository.add(project);
    }

    public String delete(Project project) {
    	Project loaded = this.repository.load(project);
    	if (!currentUser.equals(loaded.getOwner())) {
    		return "invalid";
    	}
    	this.repository.remove(loaded);
    	return "ok";
    }

    public String update(Project project) {
    	Project loaded = this.repository.load(project);
    	if (!currentUser.equals(loaded.getOwner())) {
    		return "invalid";
    	}
    	loaded.setDescription(project.getDescription());
    	this.project = loaded;
    	return "ok";
    }
	public List<User> getUsers() {
		return users;
	}

    public List<Card> getCards() {
    	return cards;
    }

    public Project getProject() {
		return project;
	}

    public List<Project> getProjects() {
        return projects;
    }

    public void list() {
        this.projects = repository.listAllFrom(currentUser);
    }

    public void addColaborator(Project project, User colaborator) {
        this.project = repository.get(project.getId());
        this.project.getColaborators().add(colaborator);
        repository.update(this.project);
    }
}
