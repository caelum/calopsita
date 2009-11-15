package br.com.caelum.calopsita.persistence.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.CardType;
import br.com.caelum.calopsita.model.FromProject;
import br.com.caelum.calopsita.model.Identifiable;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.plugins.PluginResultTransformer;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class ProjectDao implements ProjectRepository {

    private final Session session;
	private final CardDao cardDao;
	private final PluginResultTransformer transformer;

    public ProjectDao(Session session, PluginResultTransformer transformer) {
        this.session = session;
		this.transformer = transformer;
		this.cardDao = new CardDao(session, transformer);
    }

    public Project refresh(Project project) {
    	session.refresh(project);
    	return project;
    }

    public List<Card> listLastAddedCards(Project project) {
    	return this.session.createQuery("from Card c where c.project = :project order by c.id desc")
    		.setParameter("project", project)
    		.setMaxResults(5).list();
    }

    public List<Card> listTodoCardsFrom(Project project) {
    	return this.session.createQuery("from Card c where c.project = :project and c.status != 'DONE'")
    		.setResultTransformer(transformer)
			.setParameter("project", project).list();
    }

    public List<User> listUnrelatedUsers(Project project) {
		String hql = "select u from User u, Project p where p = :project " +
				"and u != p.owner and u not in elements (p.colaborators)";
		Query query = session.createQuery(hql);
		query.setParameter("project", project);
		return query.list();
	}

    public Project get(Long id) {
    	return (Project) session.get(Project.class, id);
    }

    public Project load(Project project) {
    	return (Project) session.load(Project.class, project.getId());
    }

    public void add(Project project) {
        this.session.save(project);
    }

    public void update(Project project) {
        this.session.update(project);
    }

    public void remove(Project project) {
    	this.session.createQuery("delete from Card s where s.project = :project")
			.setParameter("project", project).executeUpdate();
    	this.session.createQuery("delete from Iteration i where i.project = :project")
    		.setParameter("project", project).executeUpdate();
    	this.session.delete(project);
    }

	public List<Card> listRootCardsFrom(Project project) {
		return cardDao.listRootFrom(project);
	}

    public List<Iteration> listIterationsFrom(Project project) {
        return this.session.createQuery("from Iteration i where i.project = :project")
        .setParameter("project", project)
        .setResultTransformer(transformer)
        .list();
    }

	public boolean hasInconsistentValues(Object[] parameters, User user) {
		if (parameters == null) {
			return false;
		}
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
		return identifiable.getId() != null && session.createCriteria(identifiable.getClass())
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

	public List<CardType> listCardTypesFrom(Project project) {
		return new CardTypeDao(session).listFrom(project);
	}

	public Iteration getCurrentIterationFromProject(Project project) {
        return (Iteration) this.session.createQuery("from Iteration i where i.project = :project and " +
        ":today >= i.startDate and (i.endDate IS NULL OR :today <= i.endDate)")
        .setParameter("project", project).setParameter("today", new LocalDate())
        .setResultTransformer(transformer)
        .uniqueResult();
    }

	public List<Card> planningCardsWithoutIteration(Project project) {
		return session.createQuery("select c.card from PlanningCard c " +
				"where c.card.project = :project and c.card.iteration is null")
				.setParameter("project", project).setResultTransformer(transformer).list();
	}
}
