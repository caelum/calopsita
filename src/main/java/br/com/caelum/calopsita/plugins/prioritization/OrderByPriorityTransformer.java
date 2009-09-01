package br.com.caelum.calopsita.plugins.prioritization;

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

}
