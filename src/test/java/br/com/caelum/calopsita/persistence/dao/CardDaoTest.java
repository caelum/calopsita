package br.com.caelum.calopsita.persistence.dao;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Gadget;
import br.com.caelum.calopsita.model.Gadgets;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.PrioritizableCard;
import br.com.caelum.calopsita.model.Project;

public class CardDaoTest {

	private Session session;
	private CardDao dao;
	private Transaction transaction;

	@Before
	public void setUp() throws Exception {
		session = new AnnotationConfiguration().configure().buildSessionFactory().openSession();

		dao = new CardDao(session);
		transaction = session.beginTransaction();
	}


	@After
	public void tearDown() throws Exception {
		if (transaction != null) {
			transaction.rollback();
		}
	}

	@Test
	public void cardsWithoutIteration() throws Exception {
		Iteration iteration = givenAnIteration();
		Card card = givenACard(iteration.getProject());
		Card cardOfIteration = givenACardOfTheIteration(iteration);
		Card cardOfOtherProject = givenACard(givenAProject());

		List<Card> list = dao.cardsWithoutIteration(iteration.getProject());

		assertThat(list, hasItem(card));
		assertThat(list, not(hasItem(cardOfIteration)));
		assertThat(list, not(hasItem(cardOfOtherProject)));
	}
	@Test
	public void listingSubcard() throws Exception {
		Card card = givenACard();
		Card subcard = givenASubcard(card);
		Card otherCard = givenACard();
		List<Card> list = dao.listSubcards(card);

		assertThat(list, hasItem(subcard));
		assertThat(list, not(hasItem(card)));
		assertThat(list, not(hasItem(otherCard)));
	}

	@Test
	public void orderedListings() throws Exception {
		Project project = givenAProject();
		Card card3 = givenACard(project, withPriority(3));
		Card card1 = givenACard(project, withPriority(1));

		assertOrdered(card3, card1, dao.listFrom(project));
		assertOrdered(card3, card1, dao.cardsWithoutIteration(project));
	}

	@Test
	public void listingGadgets() throws Exception {
		Card card = givenACard(givenAProject(), withPriority(1));

		List<Gadget> gadgets = dao.listGadgets(card);


		assertThat(gadgets.size(), is(1));
		assertThat(gadgets, hasItem(instanceOf(PrioritizableCard.class)));
	}
	@Test
	public void updatingGadgets() throws Exception {
		Card card = givenACard(givenAProject());

		whenIAddPriorizationGadget(card);

		List<Gadget> gadgets = dao.listGadgets(card);
		assertThat(gadgets.size(), is(1));
		assertThat(gadgets, hasItem(instanceOf(PrioritizableCard.class)));

		whenIRemoveAllGadgets(card);

		assertThat(dao.listGadgets(card).size(), is(0));

	}


	private void whenIAddPriorizationGadget(Card card) {
		dao.updateGadgets(card, Arrays.asList(Gadgets.PRIORITIZATION));
	}

	private void whenIRemoveAllGadgets(Card card) {
		dao.updateGadgets(card, new ArrayList<Gadgets>());
	}

	private <T> Matcher<T> instanceOf(Class<T> type) {
		return Matchers.instanceOf(type);
	}

	private void assertOrdered(Card card3, Card card1, List<Card> list) {
		assertThat(list.size(), is(2));
		assertThat(list.get(0), is(card1));
		assertThat(list.get(1), is(card3));
	}

	private Card givenACard(Project project, int priority) {
		Card card = givenACard(project);

		PrioritizableCard pCard = new PrioritizableCard();
		pCard.setCard(card);
		pCard.setPriority(priority);
		session.save(pCard);
		session.flush();
		return card;
	}


	private int withPriority(int i) {
		return i;
	}


	private Card givenASubcard(Card card) {
		Card sub = givenACard();
		sub.setParent(card);
		session.flush();
		return sub;
	}


	private Card givenACardOfTheIteration(Iteration iteration) {
		Card card = givenACard(iteration.getProject());
		card.setIteration(iteration);
		session.update(card);
		session.flush();
		return card;
	}



	private Card givenACard() {
		Card card = new Card();
		card.setName("Rumpelstitlskin");
		card.setDescription("I hope I spelld his name correctly");
		session.save(card);
		session.flush();
		return card;

	}
	private Card givenACard(Project project) {
		Card card = givenACard();
		card.setProject(project);
		session.flush();
		return card;
	}


	private Iteration givenAnIteration() {
		Iteration iteration = new Iteration();
		iteration.setProject(givenAProject());
		session.save(iteration);
		session.flush();
		return iteration;
	}

	private Project givenAProject() {
		Project project = new Project();
		project.setName("A project");
		session.save(project);
		session.flush();
		return project;
	}
}
