package br.com.caelum.calopsita.controller;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.IterationRepository;

@Component
@InterceptedBy( { HibernateInterceptor.class, AuthenticationInterceptor.class, AuthorizationInterceptor.class })
public class IterationLogic {

    private Project project;
    private final IterationRepository repository;

    public IterationLogic(IterationRepository repository) {
        this.repository = repository;
    }

    public void save(Iteration iteration, Project project) {
        this.project = project;
        iteration.setProject(project);
        repository.add(iteration);
    }
    
    public Project getProject() {
        return project;
    }
}
