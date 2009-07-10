package br.com.caelum.calopsita.infra.di;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
	private org.hibernate.classic.Session session;

	public SessionCreator(SessionFactory factory) {
		this.factory = factory;
	}

	@PostConstruct
	public void create() {
		this.session = factory.openSession();
	}
	@Override
	public Session getInstance() {
		return session;
	}
	@PreDestroy
	public void destroy() {
		this.session.close();
	}


}
