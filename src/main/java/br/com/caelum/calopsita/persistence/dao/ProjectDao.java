package br.com.caelum.calopsita.persistence.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.FromProject;
import br.com.caelum.calopsita.model.Identifiable;
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

	@Override
	public boolean hasInconsistentValues(Object[] parameters, User user) {
		for (Object object : parameters) {
			if (object instanceof Project) {
				Project project = (Project) object;
				if (project.getId() != null) {
					return userNotAllowed(user, project);
				}
			} else if (object instanceof FromProject) {
				FromProject fromProject = (FromProject) object;
				if (fromProject.getProject() == null || fromProject.getProject().getId() == null) {
					throw new IllegalArgumentException("You must set the project id");
				}

				return inconsistentProject(fromProject, fromProject.getProject()) || userNotAllowed(user, fromProject.getProject());
			}
		}
		return false;
	}

	private boolean inconsistentProject(Identifiable identifiable, Project project) {
		return identifiable.getId() == null || session.createCriteria(identifiable.getClass())
			.add(Restrictions.eq("id", identifiable.getId()))
			.add(Restrictions.eq("project", project))
			.uniqueResult() == null;
	}

	private boolean userNotAllowed(User user, Project project) {
		return user == null || session.createQuery("from Project p where p = :project and (" +
				"p.owner = :user or :user in elements(p.colaborators))")
				.setParameter("project", project)
				.setParameter("user", user)
				.uniqueResult() == null;
	}
}
