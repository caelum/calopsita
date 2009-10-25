package br.com.caelum.calopsita.persistence.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import br.com.caelum.calopsita.infra.interceptor.RepositoryInterceptor;
import br.com.caelum.iogi.Instantiator;

public abstract class AbstractDaoTest {

	private static SessionFactory sessionFactory;
	protected Session session;
	private Transaction transaction;
	protected Mockery mockery;
	private Instantiator<Object> instantiator;

	@BeforeClass
    public static void prepare() {
        sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
    }

	@AfterClass
    public static void destroy() {
    	sessionFactory.close();
    }

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		instantiator = mockery.mock(Instantiator.class);

		session = sessionFactory.openSession(new RepositoryInterceptor(instantiator));

		transaction = session.beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		if (transaction != null) {
			transaction.rollback();
		}
		session.close();
	}

}
