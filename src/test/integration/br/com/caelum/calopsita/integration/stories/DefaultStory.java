package br.com.caelum.calopsita.integration.stories;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import br.com.caelum.calopsita.integration.BrowserFactory;
import br.com.caelum.calopsita.integration.HtmlUnitFactory;
import br.com.caelum.seleniumdsl.Browser;

public class DefaultStory {
    protected GivenContexts given;
    protected WhenActions when;
    protected ThenAsserts then;
    private BrowserFactory factory;
    private static SessionFactory sessionFactory;
    protected Session session;
    private Transaction transaction;
	private static AnnotationConfiguration cfg;

    @BeforeClass
    public static void prepare() {
        cfg = new AnnotationConfiguration().configure(DefaultStory.class
                .getResource("/hibernate.cfg.test.xml"));
        sessionFactory = cfg.buildSessionFactory();
    }
    
    @AfterClass
    public static void destroy() {
    	new SchemaExport(cfg).create(false, true); //clearing database
    }

    @Before
    public void setUp() {
        factory = new HtmlUnitFactory();
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
