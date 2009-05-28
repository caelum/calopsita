package br.com.caelum.calopsita.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.PrioritizableCard;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.PrioritizationRepository;

public class PrioritizationDao implements PrioritizationRepository {

	private static final ResultTransformer TRANSFORMER = new ListResultTransformer();

	private final Session session;

	public PrioritizationDao(Session session) {
		this.session = session;
	}

	@Override
	public PrioritizableCard load(PrioritizableCard card) {
		return (PrioritizableCard) session.load(PrioritizableCard.class, card.getId());
	}

	public List<List<Card>> listCards(Project project) {
		return session.createQuery("from PrioritizableCard c where c.card.project = :project order by c.priority")
			.setParameter("project", project)
			.setResultTransformer(TRANSFORMER)
			.list();
	}

	private static class ListResultTransformer implements ResultTransformer {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		@Override
		public List transformList(List list) {
			List<List<Card>> result = new ArrayList<List<Card>>();
			if (!list.isEmpty()) {
				List<PrioritizableCard> cards = list;
				for (int i = 0; i < cards.get(list.size() - 1).getPriority() + 1; i++) {
					result.add(new ArrayList<Card>());
				}
				for (PrioritizableCard card : cards) {
					result.get(card.getPriority()).add(card.getCard());
				}
			}
			return result;
		}

		@Override
		public Object transformTuple(Object[] objs, String[] names) {
			return objs[0];
		}

	}

}
