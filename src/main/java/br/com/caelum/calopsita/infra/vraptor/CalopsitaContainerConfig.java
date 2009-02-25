package br.com.caelum.calopsita.infra.vraptor;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.picocontainer.Characteristics;
import org.picocontainer.MutablePicoContainer;

import br.com.caelum.calopsita.infra.di.SessionInjector;
import br.com.caelum.calopsita.persistence.dao.ProjectDao;
import br.com.caelum.calopsita.repository.ProjectRepository;

public class CalopsitaContainerConfig implements br.com.caelum.calopsita.infra.pico.ContainerConfig {

    public void configureApplicationContainer(MutablePicoContainer container) {
        container.as(Characteristics.CACHE).addComponent(SessionFactory.class.getName(),
                new AnnotationConfiguration().configure().buildSessionFactory());

        container.addAdapter(new SessionInjector());

        // register DAOs
        container.addComponent(ProjectRepository.class.getName(), ProjectDao.class);
    }

    public void configureRequestContainer(MutablePicoContainer container) {
        container.addAdapter(new SessionInjector());

        // register DAOs
        container.addComponent(ProjectRepository.class, ProjectDao.class);
    }

}
