package br.com.caelum.calopsita.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Gadget;
import br.com.caelum.calopsita.model.Gadgets;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.CardRepository;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class CardDao implements CardRepository {

	private final Session session;

	public CardDao(Session session) {
		this.session = session;
	}

	public void add(Card card) {
		session.save(card);
	}

	public Card load(Card card) {
		return (Card) session.load(Card.class, card.getId());
	}

	public void update(Card card) {
		session.update(card);
	}

	public void remove(Card card) {
		session.delete(card);
	}

	public List<Card> listFrom(Project project) {
		return this.session.createQuery("select c from PrioritizableCard p right join p.card c " +
				"where c.project = :project order by p.priority, c.id")
			.setParameter("project", project).list();
	}

	public List<Card> planningCardsWithoutIteration(Project project) {
		return session.createQuery("select c.card from PlanningCard c left join c.prioritizableCard p " +
				"where c.card.project = :project and c.card.iteration is null " +
				"order by p.priority, c.card.id")
				.setParameter("project", project).list();
	}

	public List<Card> listSubcards(Card card) {
		return session.createQuery("from Card s where s.parent = :card")
			.setParameter("card", card).list();
	}

	public <T extends Gadget> T load(T gadget) {
		return (T) session.load(gadget.getClass(), gadget.getCard().getId());
	}

	public void orderCardsByPriority(Iteration iteration) {
		session.evict(iteration);
		List<Card> cards = session.createQuery("select c from PrioritizableCard p right join p.card c " +
				"where c.iteration = :iteration " +
				"order by p.priority, c.id").setParameter("iteration", iteration).list();
		iteration.setCards(cards);
	}

	public void add(Gadget gadget) {
		session.save(gadget);
	}

	public List<Gadget> listGadgets(Card card) {
		return session.createCriteria(Gadget.class).add(Restrictions.eq("id", card.getId())).list();
	}

	public void updateGadgets(Card card, List<Gadgets> gadgets) {
		if (gadgets == null) {
			gadgets = new ArrayList<Gadgets>();
		}
		List<Gadgets> cardGadgets = Gadgets.valueOf(listGadgets(card));
		for (Gadgets gadget : gadgets) {
			if (!cardGadgets.contains(gadget)) {
				add(gadget.createGadgetFor(card));
			}
		}
		for (Gadgets gadget : cardGadgets) {
			if (!gadgets.contains(gadget)) {
				session.createQuery("delete from " + gadget.gadgetClass().getSimpleName()
						+ " where id = :id").setParameter("id", card.getId()).executeUpdate();
			}
		}
	}

}
