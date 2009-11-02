package br.com.caelum.calopsita.persistence.dao;

import java.util.List;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.plugins.PluginResultTransformer;
import br.com.caelum.calopsita.repository.IterationRepository;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class IterationDao implements IterationRepository{

    private final Session session;
	private final PluginResultTransformer transformer;

    public IterationDao(Session session, PluginResultTransformer transformer) {
        this.session = session;
		this.transformer = transformer;
    }

    public void add(Iteration iteration) {
        session.save(iteration);
    }

    public void remove(Iteration iteration) {
    	session.delete(iteration);
    }

    public void update(Iteration iteration) {
        session.update(iteration);
    }

    public Iteration load(Iteration iteration) {
    	return (Iteration) session.load(Iteration.class, iteration.getId());
    }

    public List<Card> listCards(Iteration iteration) {
		return session.createQuery("from Card c where c.iteration = :iteration ")
				.setParameter("iteration", iteration)
				.setResultTransformer(transformer)
				.list();
	}

}
