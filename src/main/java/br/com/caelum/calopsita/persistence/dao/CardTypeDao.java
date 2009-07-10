package br.com.caelum.calopsita.persistence.dao;

import java.util.List;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.CardType;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.CardTypeRepository;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class CardTypeDao implements CardTypeRepository {

	private final Session session;
	public CardTypeDao(Session session) {
		this.session = session;
	}

	public void save(CardType cardType) {
		this.session.save(cardType);
	}

	@Override
	public List<CardType> listFrom(Project project) {
		return session.createQuery("from CardType type where type.project = :project")
			.setParameter("project", project)
			.list();
	}
}
