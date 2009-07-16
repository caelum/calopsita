package br.com.caelum.calopsita.persistence.dao;

import org.hibernate.Session;
import org.joda.time.LocalDate;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.IterationRepository;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class IterationDao implements IterationRepository{

    private final Session session;

    public IterationDao(Session session) {
        this.session = session;
    }

    public void add(Iteration iteration) {
        session.save(iteration);
    }

    public void remove(Iteration iteration) {
    	session.delete(iteration);
    }

    public void update(Iteration iteration) {
        session.merge(iteration);
    }

    public Iteration load(Iteration iteration) {
    	return (Iteration) session.load(Iteration.class, iteration.getId());
    }

    public Iteration getCurrentIterationFromProject(Project project) {
        return (Iteration) this.session.createQuery("from Iteration i where i.project = :project and " +
        ":today >= i.startDate and (i.endDate IS NULL OR :today <= i.endDate)")
        .setParameter("project", project).setParameter("today", new LocalDate()).uniqueResult();
    }
}
