package br.com.caelum.calopsita.plugins.prioritization;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.persistence.dao.CardDao;
import br.com.caelum.calopsita.plugins.Transformer;

public class OrderByPriorityTransformer implements Transformer<Card> {

	public boolean accepts(Class<?> type) {
		return type.equals(Card.class);
	}

	public List<Card> transform(List<Card> list, Session session) {
		CardDao repository = new CardDao(session, Arrays.<Transformer>asList(this));
		for (Card card : list) {
			card.setRepository(repository);
		}
		Collections.sort(list, new PriorityComparator());
		return list;
	}

	public static class PriorityComparator implements Comparator<Card> {
		public int compare(Card left, Card right) {
			PrioritizableCard leftPriority = left.getGadget(PrioritizableCard.class);
			PrioritizableCard rightPriority = right.getGadget(PrioritizableCard.class);
			if (leftPriority == null || leftPriority.getPriority() == 0) {
				return 1;
			} else if (rightPriority == null || rightPriority.getPriority() == 0) {
				return -1;
			}
			return leftPriority.getPriority() - rightPriority.getPriority();
		}
	}
}
