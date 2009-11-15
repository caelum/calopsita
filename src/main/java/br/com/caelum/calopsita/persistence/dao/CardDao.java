package br.com.caelum.calopsita.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Gadget;
import br.com.caelum.calopsita.model.Gadgets;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.plugins.PluginResultTransformer;
import br.com.caelum.calopsita.repository.CardRepository;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class CardDao implements CardRepository {

	private final Session session;
	private final PluginResultTransformer transformer;

	public CardDao(Session session, PluginResultTransformer transformer) {
		this.session = session;
		this.transformer = transformer;
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

	public List<Card> listRootFrom(Project project) {
		return this.session.createQuery("from Card c where c.project = :project and c.parent is null")
			.setParameter("project", project)
			.setResultTransformer(transformer)
			.list();
	}

	public List<Card> listSubcards(Card card) {
		return session.createQuery("from Card s where s.parent = :card")
			.setResultTransformer(transformer)
			.setParameter("card", card).list();
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
