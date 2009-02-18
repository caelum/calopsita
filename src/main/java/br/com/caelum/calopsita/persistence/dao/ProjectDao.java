package br.com.caelum.calopsita.persistence.dao;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.ProjectRepository;

public class ProjectDao implements ProjectRepository {

    private final Session session;

    public ProjectDao(Session session) {
        this.session = session;
    }

    @Override
    public void add(Project project) {
        this.session.save(project);
    }

    @Override
    public void update(Project project) {
        this.session.merge(project);
    }

    @Override
    public void remove(Project project) {
    }

}
