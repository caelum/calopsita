package br.com.caelum.calopsita.persistence.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Gadget;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.CardRepository;

public class CardDao implements CardRepository {

	private final Session session;

	public CardDao(Session session) {
		this.session = session;
	}

	@Override
	public void add(Card card) {
		session.save(card);
	}
	@Override
	public Card load(Card card) {
		return (Card) session.load(Card.class, card.getId());
	}
	@Override
	public void update(Card card) {
		session.update(card);
	}

	@Override
	public void remove(Card card) {
		session.delete(card);
	}

	@Override
	public List<Card> listFrom(Project project) {
		return this.session.createQuery("select c from PrioritizableCard p right join p.card c " +
				"where c.project = :project order by p.priority")
			.setParameter("project", project).list();
	}

	@Override
	public List<Card> cardsWithoutIteration(Project project) {
		return session.createQuery("select c from PrioritizableCard p right join p.card c " +
				"where c.project = :project and c.iteration is null " +
				"order by p.priority")
				.setParameter("project", project).list();
	}

	@Override
	public List<Card> listSubcards(Card card) {
		return session.createQuery("from Card s where s.parent = :card")
			.setParameter("card", card).list();
	}

	@Override
	public <T extends Gadget> T load(T gadget) {
		return (T) session.load(gadget.getClass(), gadget.getCard().getId());
	}

	@Override
	public void orderCardsByPriority(Iteration iteration) {
		session.evict(iteration);
		List<Card> cards = session.createQuery("select c from PrioritizableCard p right join p.card c " +
				"where c.iteration = :iteration " +
				"order by p.priority").setParameter("iteration", iteration).list();
		iteration.setCards(cards);
	}

	@Override
	public void add(Gadget gadget) {
		session.save(gadget);
	}

	public List<Gadget> listGadgets(Card card) {
		return session.createCriteria(Gadget.class).add(Restrictions.eq("id", card.getId())).list();
	}

}
