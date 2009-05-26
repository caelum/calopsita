package br.com.caelum.calopsita.persistence.dao;

import java.util.List;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.vraptor.ioc.Component;

@Component
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
    public Project load(Project project) {
    	return (Project) session.load(Project.class, project.getId());
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
    	this.session.createQuery("delete from Card s where s.project = :project")
			.setParameter("project", project).executeUpdate();
    	this.session.createQuery("delete from Iteration i where i.project = :project")
    		.setParameter("project", project).executeUpdate();
    	this.session.delete(project);
    }

    @Override
    public List<Project> listAllFrom(User user) {
        return this.session.createQuery("from Project p where p.owner = :user or " +
        		":user in elements(p.colaborators)")
                .setParameter("user", user).list();
    }

	@Override
	public List<Card> listCardsFrom(Project project) {
		return new CardDao(session).listFrom(project);
	}

	@Override
    public List<Iteration> listIterationsFrom(Project project) {
        return this.session.createQuery("from Iteration i where i.project = :project")
        .setParameter("project", project).list();
    }
}
