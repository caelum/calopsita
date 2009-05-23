package br.com.caelum.calopsita.persistence.dao;

import java.util.List;

import org.hibernate.Session;
import org.joda.time.LocalDate;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.IterationRepository;

public class IterationDao implements IterationRepository{

    private final Session session;

    public IterationDao(Session session) {
        this.session = session;
    }
    
    @Override
    public void add(Iteration iteration) {
        session.save(iteration);
    }

    @Override
    public void remove(Iteration iteration) {
    	session.delete(iteration);
    }

    @Override
    public void update(Iteration iteration) {
        session.merge(iteration);
    }

    @Override
    public Iteration load(Iteration iteration) {
    	return (Iteration) session.load(Iteration.class, iteration.getId());
    }

    @Override
    public Iteration getCurrentIterationFromProject(Project project) {
        return (Iteration) this.session.createQuery("from Iteration i where i.project = :project and " +
        ":today >= i.startDate and (:today <= i.endDate or :today = null)")
        .setParameter("project", project).setParameter("today", new LocalDate()).uniqueResult();
    }
    
    @Override
    public List<Iteration> listIterationsFrom(Project project) {
        return this.session.createQuery("from Iteration i where i.project = :project")
        .setParameter("project", project).list();
    }
}
