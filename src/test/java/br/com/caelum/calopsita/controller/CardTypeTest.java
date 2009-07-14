package br.com.caelum.calopsita.controller;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.mocks.MockResult;
import br.com.caelum.calopsita.model.CardType;
import br.com.caelum.calopsita.repository.CardTypeRepository;

public class CardTypeTest {


	private Mockery mockery;
	private CardTypeRepository repository;
	private CardTypesController controller;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();

		repository = mockery.mock(CardTypeRepository.class);
		controller = new CardTypesController(new MockResult(), repository);
	}

	@Test
	public void savingACardType() throws Exception {
		CardType type = givenACardType();

		shouldSaveOnRepository(type);

		whenISaveOnController(type);
	}

	private void whenISaveOnController(CardType type) {
		controller.save(type);
	}

	private void shouldSaveOnRepository(final CardType type) {

		mockery.checking(new Expectations() {
			{
				one(repository).save(type);
			}
		});
	}

	private CardType givenACardType() {
		return new CardType();
	}

}