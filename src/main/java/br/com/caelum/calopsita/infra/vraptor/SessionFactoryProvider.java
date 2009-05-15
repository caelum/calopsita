package br.com.caelum.calopsita.infra.vraptor;

import java.lang.reflect.Type;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.picocontainer.PicoContainer;
import org.picocontainer.injectors.FactoryInjector;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

@ApplicationScoped
@Component
public class SessionFactoryProvider extends FactoryInjector<SessionFactory> {

	@Override
	public SessionFactory getComponentInstance(PicoContainer pico, Type type) {
		return new AnnotationConfiguration().configure().buildSessionFactory();
	}

	@Override
	public Object getComponentKey() {
		return SessionFactory.class.getName();
	}
}
