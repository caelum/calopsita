package br.com.caelum.calopsita.plugins.prioritization;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.repository.CardRepository;

public class PriorityComparatorTest {


	private Mockery mockery;
	private CardRepository repository;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		repository = mockery.mock(CardRepository.class);
	}

	@Test
	public void firstNullIsLessThanAnyOne() throws Exception {
		Card oneCard = givenACard();
		Card otherCard = givenACard();
		givenThisCardHasNoPriority(oneCard);
		givenThisCardHasPriority(otherCard, 2);

		List<Card> cards = Arrays.asList(oneCard, otherCard);
		whenISort(cards);
		assertThat(cards.get(0), is(otherCard));
		assertThat(cards.get(1), is(oneCard));

		cards = Arrays.asList(otherCard, oneCard);
		whenISort(cards);
		assertThat(cards.get(0), is(otherCard));
		assertThat(cards.get(1), is(oneCard));
	}
	@Test
	public void firstZeroHasLessPriorityThanAnyOne() throws Exception {
		Card oneCard = givenACard();
		Card otherCard = givenACard();
		givenThisCardHasPriority(oneCard, 0);
		givenThisCardHasPriority(otherCard, 2);

		List<Card> cards = Arrays.asList(oneCard, otherCard);
		whenISort(cards);
		assertThat(cards.get(0), is(otherCard));
		assertThat(cards.get(1), is(oneCard));

		cards = Arrays.asList(otherCard, oneCard);
		whenISort(cards);
		assertThat(cards.get(0), is(otherCard));
		assertThat(cards.get(1), is(oneCard));
	}
	@Test
	public void equalPrioritiesKeepOrder() throws Exception {
		Card oneCard = givenACard();
		Card otherCard = givenACard();
		givenThisCardHasPriority(oneCard, 2);
		givenThisCardHasPriority(otherCard, 2);

		List<Card> cards = Arrays.asList(oneCard, otherCard);
		whenISort(cards);
		assertThat(cards.get(0), is(oneCard));
		assertThat(cards.get(1), is(otherCard));

		cards = Arrays.asList(otherCard, oneCard);
		whenISort(cards);
		assertThat(cards.get(0), is(otherCard));
		assertThat(cards.get(1), is(oneCard));
	}
	@Test
	public void differentPrioritiesOrderProperly() throws Exception {
		Card oneCard = givenACard();
		Card otherCard = givenACard();
		givenThisCardHasPriority(oneCard, 1);
		givenThisCardHasPriority(otherCard, 2);

		List<Card> cards = Arrays.asList(oneCard, otherCard);
		whenISort(cards);
		assertThat(cards.get(0), is(oneCard));
		assertThat(cards.get(1), is(otherCard));

		cards = Arrays.asList(otherCard, oneCard);
		whenISort(cards);
		assertThat(cards.get(0), is(oneCard));
		assertThat(cards.get(1), is(otherCard));
	}

	private void whenISort(List<Card> cards) {
		Collections.sort(cards, new OrderByPriorityTransformer.PriorityComparator());
	}
	private void givenThisCardHasNoPriority(final Card oneCard) {
		mockery.checking(new Expectations() {
			{
				allowing(repository).listGadgets(oneCard);
				will(returnValue(Arrays.asList()));
			}
		});
	}

	private void givenThisCardHasPriority(final Card oneCard, final int priority) {
		mockery.checking(new Expectations() {
			{
				PrioritizableCard priorityCard = new PrioritizableCard(oneCard);
				priorityCard.setPriority(priority);
				allowing(repository).listGadgets(oneCard);
				will(returnValue(Arrays.asList(priorityCard)));
			}
		});
	}

	private Card givenACard() {
		Card card = new Card();
		card.setRepository(repository);
		return card;
	}
}
