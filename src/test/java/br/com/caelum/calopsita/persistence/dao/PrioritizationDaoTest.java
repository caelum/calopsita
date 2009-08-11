package br.com.caelum.calopsita.persistence.dao;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.PrioritizableCard;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Card.Status;
import br.com.caelum.calopsita.repository.PrioritizationRepository;

public class PrioritizationDaoTest extends AbstractDaoTest{

	private PrioritizationRepository dao;
	private Project project;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dao = new PrioritizationDao(session);
		project = new Project();
		project.setName("A Project");
		session.save(project);
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

	@Test
	public void listingCardsDontIncludeDoneCards() throws Exception {
		Card cardNotDone = givenACard(withPriority(1));
		Card cardDone = givenACard(withPriority(1));
		cardDone.setStatus(Status.DONE);

		List<List<Card>> list = dao.listCards(project);


		assertThat(list.size(), is(2));
		assertThat(list.get(1), hasItem(cardNotDone));
		assertThat(list.get(1), not(hasItem(cardDone)));
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
