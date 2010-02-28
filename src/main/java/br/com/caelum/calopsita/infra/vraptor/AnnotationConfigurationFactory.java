package br.com.caelum.calopsita.infra.vraptor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.cfg.AnnotationConfiguration;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@ApplicationScoped
@Component
public class AnnotationConfigurationFactory implements ComponentFactory<AnnotationConfiguration> {

	private static List<Class<?>> entities = new ArrayList<Class<?>>();

	private AnnotationConfiguration configuration;

	public AnnotationConfigurationFactory() {
		configuration = new AnnotationConfiguration();
	}

	@PostConstruct
	public void addEntitiesToConfiguration() {
		configuration.configure();
		for (Class<?> entity : entities) {
			configuration.addAnnotatedClass(entity);
		}
	}

	static void addEntity(Class<?> entity) {
		entities.add(entity);
	}

	static List<Class<?>> getEntities() {
		return entities;
	}

	@Override
	public AnnotationConfiguration getInstance() {
		return null;
	}

}
