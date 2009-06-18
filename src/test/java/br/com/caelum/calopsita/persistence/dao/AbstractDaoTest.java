package br.com.caelum.calopsita.persistence.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class AbstractDaoTest {

	private static SessionFactory sessionFactory;
	protected Session session;
	private Transaction transaction;

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
		session = new AnnotationConfiguration().configure().buildSessionFactory().openSession();

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
