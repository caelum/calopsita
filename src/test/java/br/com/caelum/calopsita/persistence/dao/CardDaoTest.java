package br.com.caelum.calopsita.persistence.dao;

import static br.com.caelum.calopsita.CustomMatchers.hasItemsInThisOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Gadget;
import br.com.caelum.calopsita.model.Gadgets;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.plugins.planning.PlanningCard;
import br.com.caelum.calopsita.plugins.prioritization.PrioritizableCard;

public class CardDaoTest extends AbstractDaoTest {
	private CardDao dao;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dao = new CardDao(session);
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
		Card card3 = givenAPlanningCard(project, withPriority(3));
		Card card1 = givenAPlanningCard(project, withPriority(1));

		assertThat(dao.listFrom(project), hasItemsInThisOrder(card1, card3));
	}

	@Test
	public void listingGadgets() throws Exception {
		Card card = givenAPlanningCard(givenAProject(), withPriority(1));

		List<Gadget> gadgets = dao.listGadgets(card);


		assertThat(gadgets.size(), is(2));
		assertThat(gadgets, hasItem(instanceOf(PrioritizableCard.class)));
		assertThat(gadgets, hasItem(instanceOf(PlanningCard.class)));
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

	private Matcher<? extends Gadget> instanceOf(Class<? extends Gadget> type) {
		 Matcher matcher = Matchers.instanceOf(type);
		 return matcher;
	}

	private Card givenAPlanningCard(Project project) {
		Card card = givenACard(project);
		session.save(new PlanningCard(card));
		session.flush();
		return card;
	}

	private Card givenAPlanningCard(Project project, int priority) {
		Card card = givenAPlanningCard(project);

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

	private Project givenAProject() {
		Project project = new Project();
		project.setName("A project");
		session.save(project);
		session.flush();
		return project;
	}
}
