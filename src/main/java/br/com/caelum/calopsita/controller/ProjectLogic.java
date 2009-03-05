package br.com.caelum.calopsita.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;

@Component
@InterceptedBy( { HibernateInterceptor.class, AuthorizationInterceptor.class })
public class ProjectLogic {

    private final ProjectRepository repository;
    private List<Project> projects;
    private final HttpSession session;

    public ProjectLogic(ProjectRepository repository, HttpSession session) {
        this.repository = repository;
        this.session = session;
    }

    public void form() {

    }

    public void save(Project project) {
        if (project.getId() != null) {
            this.repository.update(project);
        } else {
            User user = (User) this.session.getAttribute(User.class.getName());
            project.setOwner(user);
            this.repository.add(project);
        }
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void list() {
        User user = (User) session.getAttribute(User.class.getName());
        this.projects = repository.listAllFromOwner(user);
    }
}
