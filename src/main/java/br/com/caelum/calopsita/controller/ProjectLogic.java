package br.com.caelum.calopsita.controller;

import java.util.List;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.ProjectRepository;

@Component
@InterceptedBy( {HibernateInterceptor.class, AuthorizationInterceptor.class })
public class ProjectLogic {

    private final ProjectRepository repository;
	private List<Project> projects;

    public ProjectLogic(ProjectRepository repository) {
        this.repository = repository;
    }

    public void form() {

    }

    public void save(Project project) {
        if (project.getId() != null) {
            this.repository.update(project);
        } else {
            this.repository.add(project);
        }
    }

	public List<Project> getProjects() {
		return projects;
	}

	public void list() {
		this.projects = repository.listAll();
	}
}
