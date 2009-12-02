package br.com.caelum.calopsita.plugins.roi;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class RoiDao implements RoiRepository {

	private final Session session;

	public RoiDao(Session session) {
		this.session = session;
	}

	@Override
	public ValuableCard load(ValuableCard card) {
		return (ValuableCard) session.load(ValuableCard.class, card.getId());
	}

	@Override
	public void add(ValuableCard valuableCard) {
		session.save(valuableCard);
	}

	@Override
	public void remove(ValuableCard valuableCard) {
		session.delete(valuableCard);
	}

	@Override
	public void update(ValuableCard valuableCard) {
		session.saveOrUpdate(valuableCard);
	}

}
