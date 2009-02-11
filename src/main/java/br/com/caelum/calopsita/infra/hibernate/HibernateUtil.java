package br.com.caelum.calopsita.infra.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Bom e velho HibernateUtil com ThreadLocal
 */
// TODO renomear isso e usar currentSession do proprio hibernate
public class HibernateUtil {

	private Logger logger = Logger.getLogger(HibernateUtil.class);

	private SessionFactory sessionFactory;

	private ThreadLocal<Session> sessions = new ThreadLocal<Session>();

	public HibernateUtil() {
		AnnotationConfiguration configuration = new AnnotationConfiguration().configure();
		sessionFactory = configuration.buildSessionFactory();
	}

	public Session openSession() {
		if (sessions.get() != null) {
			logger.error("Já tinha uma Session nessa thread!");
		}
		
		logger.trace("hibernate util, open new session");
		sessions.set(sessionFactory.openSession());
		return sessions.get();
	}

	public void closeCurrentSession() {
		logger.trace("hibernate util closing current session");
		sessions.get().close();
		sessions.set(null);
	}

	public Session currentSession() {
		return sessions.get();
	}

	public void close() {
		sessionFactory.close();
	}
}
