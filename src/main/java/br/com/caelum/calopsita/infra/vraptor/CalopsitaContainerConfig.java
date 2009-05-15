package br.com.caelum.calopsita.infra.vraptor;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.picocontainer.Characteristics;
import org.picocontainer.MutablePicoContainer;
import org.vraptor.plugin.pico.ContainerConfig;

import br.com.caelum.calopsita.infra.di.SessionInjector;

public class CalopsitaContainerConfig implements ContainerConfig {

    public void configureApplicationContainer(MutablePicoContainer container) {
        container.as(Characteristics.CACHE).addComponent(SessionFactory.class.getName(),
                new AnnotationConfiguration().configure().buildSessionFactory());
    }

    public void configureRequestContainer(MutablePicoContainer container) {
        container.addAdapter(new SessionInjector());
    }

}
