package br.com.caelum.calopsita.persistence.dao;

import java.util.List;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;

public class ProjectDao implements ProjectRepository {

    private final Session session;

    public ProjectDao(Session session) {
        this.session = session;
    }

    @Override
    public Project get(Long id) {
    	return (Project) session.get(Project.class, id);
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

    @SuppressWarnings("unchecked")
    @Override
    public List<Project> listAllFromOwner(User user) {
        return this.session.createQuery("from Project where owner = :user")
                .setParameter("user", user).list();
    }

}
