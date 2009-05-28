package br.com.caelum.calopsita.persistence.dao;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.PrioritizableCard;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.PrioritizationRepository;

public class PrioritizationDaoTest {

	private Session session;
	private Transaction transaction;
	private PrioritizationRepository dao;
	private Project project;

	@Before
	public void setUp() throws Exception {
		session = new AnnotationConfiguration().configure().buildSessionFactory().openSession();

		dao = new PrioritizationDao(session);
		transaction = session.beginTransaction();
		project = new Project();
		project.setName("A Project");
		session.save(project);
	}


	@After
	public void tearDown() throws Exception {
		if (transaction != null) {
			transaction.rollback();
		}
	}

	@Test
	public void listingCardsGroupedByPriority() throws Exception {
		Card card1 = givenACard(withPriority(1));
		Card card4 = givenACard(withPriority(4));

		List<List<Card>> list = dao.listCards(project);


		assertThat(list.size(), is(5));

		assertThat(list.get(1), hasItem(card1));
		assertThat(list.get(4), hasItem(card4));
	}


	private Card givenACard(int priority) {
		Card card = new Card();
		card.setProject(project);
		session.save(card);
		PrioritizableCard pCard = new PrioritizableCard();
		pCard.setCard(card);
		pCard.setPriority(priority);
		session.save(pCard);
		return card;
	}


	private int withPriority(int i) {
		return i;
	}
}
