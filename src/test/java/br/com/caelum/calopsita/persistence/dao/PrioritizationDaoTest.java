package br.com.caelum.calopsita.persistence.dao;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.List;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Card.Status;
import br.com.caelum.calopsita.plugins.PluginResultTransformer;
import br.com.caelum.calopsita.plugins.Transformer;
import br.com.caelum.calopsita.plugins.prioritization.PrioritizableCard;
import br.com.caelum.calopsita.repository.PrioritizationRepository;

public class PrioritizationDaoTest extends AbstractDaoTest{

	private PrioritizationRepository dao;
	private Project project;
	private CardDao cardDao;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dao = new PrioritizationDao(session);
		PluginResultTransformer transformer = new PluginResultTransformer(session, Collections.<Transformer>emptyList());
		project = new Project(new ProjectDao(session, transformer));
		project.setName("A Project");
		cardDao = new CardDao(session, transformer);
		session.save(project);
	}

	@Test
	public void listingCardsGroupedByPriority() throws Exception {
		Card card1 = givenAPrioritizableCard(withPriority(1));
		Card card4 = givenAPrioritizableCard(withPriority(4));

		List<List<Card>> list = dao.listCards(project);

		assertThat(list.size(), is(5));

		assertThat(list.get(1), hasItem(card1));
		assertThat(list.get(4), hasItem(card4));
	}

	@Test
	public void listingOnlyCardsThatHavePriority() throws Exception {
		Card prioritizableCard = givenAPrioritizableCard(withPriority(0));
		Card nonPrioritizableCard = givenASimpleCard();

		List<List<Card>> list = dao.listCards(project);

		assertThat(list.size(), is(1));

		assertThat(list.get(0), hasItem(prioritizableCard));
		assertThat(list.get(0), doesntHaveItem(nonPrioritizableCard));
	}
	
	@Test
	public void listingCardsDontIncludeDoneCards() throws Exception {
		Card cardNotDone = givenAPrioritizableCard(withPriority(1));
		Card cardDone = givenAPrioritizableCard(withPriority(1));
		cardDone.setStatus(Status.DONE);

		List<List<Card>> list = dao.listCards(project);

		assertThat(list.size(), is(2));
		assertThat(list.get(1), hasItem(cardNotDone));
		assertThat(list.get(1), not(hasItem(cardDone)));
	}


	private Card givenAPrioritizableCard(int priority) {
		Card card = givenASimpleCard();
		PrioritizableCard pCard = new PrioritizableCard();
		pCard.setCard(card);
		pCard.setPriority(priority);
		session.save(pCard);
		return card;
	}

	private Card givenASimpleCard() {
		Card card = new Card(cardDao);
		card.setProject(project);
		session.save(card);
		return card;
	}


	private int withPriority(int i) {
		return i;
	}

	private Matcher<Iterable<Card>> doesntHaveItem(Card nonPrioritizableCard) {
		return not(hasItem(nonPrioritizableCard));
	}

}