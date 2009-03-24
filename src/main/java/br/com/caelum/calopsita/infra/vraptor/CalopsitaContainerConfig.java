package br.com.caelum.calopsita.infra.vraptor;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.picocontainer.Characteristics;
import org.picocontainer.MutablePicoContainer;
import org.vraptor.plugin.pico.ContainerConfig;

import br.com.caelum.calopsita.infra.di.SessionInjector;
import br.com.caelum.calopsita.persistence.dao.IterationDao;
import br.com.caelum.calopsita.persistence.dao.ProjectDao;
import br.com.caelum.calopsita.persistence.dao.StoryDao;
import br.com.caelum.calopsita.persistence.dao.UserDao;
import br.com.caelum.calopsita.repository.IterationRepository;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.calopsita.repository.StoryRepository;
import br.com.caelum.calopsita.repository.UserRepository;

public class CalopsitaContainerConfig implements ContainerConfig {

    public void configureApplicationContainer(MutablePicoContainer container) {
        container.as(Characteristics.CACHE).addComponent(SessionFactory.class.getName(),
                new AnnotationConfiguration().configure().buildSessionFactory());
    }

    public void configureRequestContainer(MutablePicoContainer container) {
        container.addAdapter(new SessionInjector());

        // register DAOs
        container.addComponent(ProjectRepository.class.getName(), ProjectDao.class);
        container.addComponent(UserRepository.class.getName(), UserDao.class);
        container.addComponent(StoryRepository.class.getName(), StoryDao.class);
        container.addComponent(IterationRepository.class.getName(), IterationDao.class);
    }

}
