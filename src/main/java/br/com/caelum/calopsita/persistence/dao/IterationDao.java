package br.com.caelum.calopsita.persistence.dao;

import java.util.List;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Iteration;
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

    public List<Card> listCardsOrderedByPriority(Iteration iteration) {
		return (List<Card>) session.createQuery("select c from PrioritizableCard p right join p.card c " +
				"where c.iteration = :iteration " +
				"order by p.priority, c.id").setParameter("iteration", iteration).list();
	}

}
