package br.com.caelum.calopsita.infra.di;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.PicoContainer;

import br.com.caelum.calopsita.dao.ProjectDao;

public class DIStartupListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent arg0) {
    }

    public void contextInitialized(ServletContextEvent event) {

        ServletContext servletContext = event.getServletContext();

        DefaultPicoContainer pico = new DefaultPicoContainer();
        pico.as(Characteristics.CACHE).addComponent(SessionFactory.class, new AnnotationConfiguration().configure().buildSessionFactory());
        pico.addAdapter(new SessionInjector());

        // registra DAOs
        pico.addComponent(ProjectDao.class);

        servletContext.setAttribute(PicoContainer.class.getName(), pico);
    }
}
