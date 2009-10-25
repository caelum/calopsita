package br.com.caelum.calopsita.infra.interceptor;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.iogi.Instantiator;
import br.com.caelum.iogi.parameters.Parameters;
import br.com.caelum.iogi.reflection.Target;

public class RepositoryInterceptorTest {


	private Session session;
	private Mockery mockery;
	private SessionFactory factory;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		factory = new AnnotationConfiguration().configure().buildSessionFactory();

		session = factory.openSession();
	}

	@Test
	public void shouldInjectRepositoryWhenLoading() throws Exception {
		Card card = givenACard();
		session.save(card);
		session.close();

		final Instantiator instantiator = mockery.mock(Instantiator.class);

		Session otherSession = factory.openSession(new RepositoryInterceptor(instantiator));


		mockery.checking(new Expectations() {
			{
				one(instantiator).instantiate(with(any(Target.class)), with(any(Parameters.class)));
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
