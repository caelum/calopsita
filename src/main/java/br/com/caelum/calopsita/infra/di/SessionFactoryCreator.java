package br.com.caelum.calopsita.infra.di;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
@ApplicationScoped
public class SessionFactoryCreator implements ComponentFactory<SessionFactory> {
	private final SessionFactory factory;

	public SessionFactoryCreator() {
		factory = new AnnotationConfiguration().configure().buildSessionFactory();
	}

	@Override
	public SessionFactory getInstance() {
		return factory;
	}

}
