package br.com.caelum.calopsita.logic;

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
import br.com.caelum.calopsita.repository.UserRepository;

@Component
@InterceptedBy( { HibernateInterceptor.class, AuthenticationInterceptor.class, AuthorizationInterceptor.class })
public class ProjectLogic {

    private final ProjectRepository repository;
    private List<Project> projects;
    private final User currentUser;
	private Project project;
	private final UserRepository userRepository;
	private List<User> users;
	private List<Card> stories;

    public ProjectLogic(ProjectRepository repository, UserRepository userRepository, User user) {
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
        this.stories = this.repository.listStoriesFrom(project);
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

    public List<Card> getStories() {
    	return stories;
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
