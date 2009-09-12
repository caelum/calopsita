package br.com.caelum.calopsita.plugins.prioritization;

import java.util.Comparator;
import java.util.List;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.plugins.Transformer;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class OrderByPriorityTransformer implements Transformer<Card> {

	public boolean accepts(Class<?> type) {
		return type.equals(Card.class);
	}

	public List<Card> transform(List<Card> list, Session session) {
		if (list.isEmpty()) {
			return list;
		}
		List<Card> result = session.createQuery("select c from PrioritizableCard p join p.card c where " +
				"c.id in (:cards) order by p.priority").setParameterList("cards", Lists.transform(list, new Function<Card, Long>() {
					public Long apply(Card card) {
						return card.getId();
					}
				}))
				.list();
		list.removeAll(result);
		result.addAll(list);
		return result;
	}

	public static class PriorityComparator implements Comparator<Card> {
		public int compare(Card left, Card right) {
			PrioritizableCard leftPriority = left.getGadget(PrioritizableCard.class);
			PrioritizableCard rightPriority = right.getGadget(PrioritizableCard.class);
			if (leftPriority == null) {
				return 1;
			} else if (rightPriority == null) {
				return -1;
			}
			return leftPriority.getPriority() - rightPriority.getPriority();
		}
	}
}
