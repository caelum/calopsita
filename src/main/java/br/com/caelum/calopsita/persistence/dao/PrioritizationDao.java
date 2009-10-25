package br.com.caelum.calopsita.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.list.LazyList;
import org.hibernate.Session;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.plugins.prioritization.PrioritizableCard;
import br.com.caelum.calopsita.repository.PrioritizationRepository;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class PrioritizationDao implements PrioritizationRepository {

	private final Session session;

	public PrioritizationDao(Session session) {
		this.session = session;
	}

	public PrioritizableCard load(PrioritizableCard card) {
		return (PrioritizableCard) session.load(PrioritizableCard.class, card.getId());
	}

	public List<List<Card>> listCards(Project project) {
		List<List<Card>> result = LazyList.decorate(new ArrayList<List<Card>>(), new Factory() {
			public Object create() {
				return new ArrayList<Card>();
			}
		});
		for (Card card : project.getToDoCards()) {
			PrioritizableCard gadget = card.getGadget(PrioritizableCard.class);
			result.get(gadget.getPriority()).add(card);
		}
		for (int i = 0; i < result.size(); i++) {
			result.get(i);
		}
		return result;
	}

}
