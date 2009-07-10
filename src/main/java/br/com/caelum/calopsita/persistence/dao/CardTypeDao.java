package br.com.caelum.calopsita.persistence.dao;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.CardType;
import br.com.caelum.calopsita.repository.CardTypeRepository;

public class CardTypeDao implements CardTypeRepository {

	private final Session session;
	public CardTypeDao(Session session) {
		this.session = session;
	}

	public void save(CardType cardType) {
		this.session.save(cardType);
	}

}
