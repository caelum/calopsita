package br.com.caelum.calopsita.infra.di;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.PicoContainer;

import br.com.caelum.calopsita.infra.hibernate.HibernateUtil;

public class DIStartupListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(DIStartupListener.class);

    public void contextDestroyed(ServletContextEvent arg0) {
    }

    public void contextInitialized(ServletContextEvent event) {

        ServletContext servletContext = event.getServletContext();

        // TODO consigo colocar essas coisas no pico?
        HibernateUtil hibernateUtil = new HibernateUtil();
        servletContext.setAttribute(HibernateUtil.class.getName(),
                hibernateUtil);

        // TODO jogar pico pra outro lado
        DefaultPicoContainer pico = new DefaultPicoContainer();
        pico.as(Characteristics.CACHE).addComponent(hibernateUtil);
        servletContext.setAttribute(PicoContainer.class.getName(), pico);
        servletContext.setAttribute(DefaultPicoContainer.class.getName(), pico);

        // registra DAOs
        /*
         * pico.addComponent(CursoDao.class);
         * pico.addComponent(DepoimentoDao.class);
         * pico.addComponent(NewsletterDao.class);
         * pico.addComponent(UsuarioAdminDao.class);
         * pico.addComponent(UsuarioDao.class);
         * pico.addComponent(NoticiaDao.class);
         * pico.addComponent(DownloadDao.class);
         */

        // registra adapter de session
        pico.addAdapter(new SessionPicoAdapter());

    }
}
