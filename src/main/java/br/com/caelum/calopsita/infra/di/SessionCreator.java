package br.com.caelum.calopsita.infra.di;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
public class SessionCreator implements ComponentFactory<Session> {

	/**
	 * random
	 */
	private static final long serialVersionUID = -4411865493965531937L;
	private final SessionFactory factory;

	public SessionCreator(SessionFactory factory) {
		this.factory = factory;
	}

	@Override
	public Session getInstance() {
		return factory.getCurrentSession();
	}


}
