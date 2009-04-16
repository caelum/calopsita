package br.com.caelum.calopsita.persistence.dao;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Iteration;
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
    }

    @Override
    public void update(Iteration iteration) {
        session.merge(iteration);
    }

	@Override
	public Iteration find(String goal) {
		return (Iteration) this.session.createQuery("from Iteration where goal = :goal").setParameter("goal", goal).uniqueResult();
	}

}
