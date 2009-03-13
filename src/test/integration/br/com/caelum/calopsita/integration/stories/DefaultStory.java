package br.com.caelum.calopsita.integration.stories;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import br.com.caelum.calopsita.integration.SeleniumFactory;
import br.com.caelum.seleniumdsl.Browser;

public class DefaultStory {
    protected GivenContexts given;
    protected WhenActions when;
    protected ThenAsserts then;
    private SeleniumFactory factory;
    private static SessionFactory sessionFactory;
    protected Session session;
    private Transaction transaction;

    @BeforeClass
    public static void prepare() {
        AnnotationConfiguration cfg = new AnnotationConfiguration().configure(DefaultStory.class
                .getResource("/hibernate.cfg.test.xml"));
        sessionFactory = cfg.buildSessionFactory();
    }

    @Before
    public void setUp() {
        factory = new SeleniumFactory();
        Browser browser = factory.getBrowser();
        session = sessionFactory.openSession();
        given = new GivenContexts(browser, session);
        when = new WhenActions(browser, session);
        then = new ThenAsserts(browser);
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() {
    	if (transaction != null) {
			transaction.rollback();
		}
        factory.close();
    }
}
