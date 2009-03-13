package br.com.caelum.calopsita.controller;

import java.util.List;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;

@Component
@InterceptedBy( { HibernateInterceptor.class, AuthenticationInterceptor.class, AuthorizationInterceptor.class })
public class ProjectLogic {

    private final ProjectRepository repository;
    private List<Project> projects;
    private final User currentUser;
	private Project project;

    public ProjectLogic(ProjectRepository repository, User user) {
        this.repository = repository;
        this.currentUser = user;
    }

    public void form() {

    }

    public void save(Project project) {
        project.setOwner(currentUser);
        this.repository.add(project);
    }

    public void view(Project project) {
    	this.project = this.repository.get(project.getId());
    }
    
    public Project getProject() {
		return project;
	}
    
    public List<Project> getProjects() {
        return projects;
    }

    public void list() {
        this.projects = repository.listAllFromOwner(currentUser);
    }
}
