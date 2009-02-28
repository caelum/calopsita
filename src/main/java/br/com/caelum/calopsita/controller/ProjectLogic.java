package br.com.caelum.calopsita.controller;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.plugin.pico.PicoContainerInterceptor;

import br.com.caelum.calopsita.infra.hibernate.HibernateInterceptor;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.ProjectRepository;

@Component
@InterceptedBy( { PicoContainerInterceptor.class, HibernateInterceptor.class })
public class ProjectLogic {

    private final ProjectRepository repository;

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
}
