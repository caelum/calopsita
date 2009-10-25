package br.com.caelum.calopsita.infra.interceptor;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.infra.vraptor.Injector;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.repository.CardRepository;
import br.com.caelum.vraptor.ioc.Container;

public class RepositoryInterceptorTest {


	private Session session;
	private Mockery mockery;
	private Container container;
	private SessionFactory factory;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		container = mockery.mock(Container.class);
		factory = new AnnotationConfiguration().configure().buildSessionFactory();

		session = factory.openSession();
	}

	@Test
	public void shouldInjectRepositoryWhenLoading() throws Exception {
		Card card = givenACard();
		session.save(card);
		session.close();

		Session otherSession = factory.openSession(new RepositoryInterceptor(new Injector(container)));


		mockery.checking(new Expectations() {
			{
				one(container).instanceFor(CardRepository.class);

				ignoring(anything());
			}
		});
		otherSession.load(Card.class, card.getId());

	}

	private Card givenACard() {
		Card card = new Card();
		card.setName("Abc");
		card.setDescription("Def");
		session.save(card);
		return card;
	}
}
