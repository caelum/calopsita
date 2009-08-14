package br.com.caelum.calopsita.logic;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.controller.CardsController;
import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.mocks.MockResult;
import br.com.caelum.calopsita.mocks.MockValidator;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Gadgets;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.CardRepository;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.vraptor.validator.ValidationError;

public class CardTest {
    private Mockery mockery;
    private CardsController logic;
	private CardRepository repository;
	private Card currentCard;
	private ProjectRepository projectRepository;
    private Project project;
	private HttpSession session;
	private User currentUser;
	private MockValidator validator;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
        repository = mockery.mock(CardRepository.class);
        session = mockery.mock(HttpSession.class);
		SessionUser sessionUser = new SessionUser(session);
        currentUser = new User();
        currentUser.setLogin("me");
        project = new Project();

		projectRepository = mockery.mock(ProjectRepository.class);
		project.setRepository(projectRepository);

		mockery.checking(new Expectations() {
			{
				allowing(session).getAttribute("currentUser");
				will(returnValue(currentUser));
			}
		});



		validator = new MockValidator();
		logic = new CardsController(new MockResult(), validator, sessionUser);
    }


    @Test
	public void savingACard() throws Exception {
    	Project project = givenAProject();
		Card card = givenACard();

		shouldSaveOnTheRepositoryTheCard(card);

		whenISaveTheCard(card, onThe(project));

		assertThat(card.getProject(), is(project));
		mockery.assertIsSatisfied();
	}

    @Test
	public void editingACardsDescription() throws Exception {
    	Card card = givenACard();
    	givenTheCard(card).withName("Huck Finn")
    						.withDescription("He is Tom Sawyer's best mate.");

    	Card loadedCard = shouldLoadTheCard(card);
    	shouldUpdateOnTheRepositoryTheCard(loadedCard);

    	whenIEditTheCard(card, changingNameTo("Huckleberry Finn"), changingDescriptionTo("He has a drunk father."));

		assertThat(loadedCard.getName(), is("Huckleberry Finn"));
		assertThat(loadedCard.getDescription(), is("He has a drunk father."));
		mockery.assertIsSatisfied();
	}

    @Test
	public void removeACardFromMyProject() throws Exception {
		Card card = givenACard();
		givenTheProjectIsOwnedBy(currentUser);

		Card returned = givenTheCardIsInThisProject(card);

		shouldRemoveTheCardFromRepository(returned);

		whenIRemove(card);

        mockery.assertIsSatisfied();
	}

    @Test(expected=ValidationError.class)
    public void removeACardFromOtherProjectThanMine() throws Exception {
        Card card = givenACard();
        givenTheProjectIsOwnedBy(anyUser());

        Card returned = givenTheCardIsInThisProject(card);

    	shouldNotRemoveTheCardFromRepository(returned);

    	whenIRemove(card);

        mockery.assertIsSatisfied();
    }
    @Test
    public void removeACardAndSubcards() throws Exception {
    	Card card = givenACard();
    	givenTheProjectIsOwnedBy(currentUser);

    	Card subcard = givenACard();
    	subcard.setParent(card);

    	Card returned = givenTheCardIsInThisProject(card);
    	givenTheCardHasSubCard(returned, subcard);

    	shouldRemoveTheCardFromRepository(returned);
    	shouldRemoveTheCardFromRepository(subcard);

    	logic.delete(card, true);

        mockery.assertIsSatisfied();
    }
    private void givenTheCardHasSubCard(final Card returned, final Card subcard) {

		mockery.checking(new Expectations() {
			{
				one(repository).listSubcards(returned);
				will(returnValue(Arrays.asList(subcard)));
			}
		});
	}


	@Test
    public void removeACardButNotSubcards() throws Exception {
    	Card card = givenACard();
    	givenTheProjectIsOwnedBy(currentUser);

    	Card subCard = givenACard();
    	subCard.setParent(card);

    	Card returned = givenTheCardIsInThisProject(card);
    	givenTheCardHasSubCard(returned, subCard);

    	shouldRemoveTheCardFromRepository(returned);
    	shouldUpdateTheCardFromRepository(subCard);

    	logic.delete(card, false);

    	assertThat(subCard.getParent(), is(nullValue()));

        mockery.assertIsSatisfied();
    }

    @Test
	public void savingACardWithGadgets() throws Exception {
    	Project project = givenAProject();
		Card card = givenACard();

		Gadgets prioritization = Gadgets.PRIORITIZATION;

		shouldSaveOnTheRepositoryTheCard(card);
		shouldSaveAGadgetOfType(prioritization);

		whenISaveTheCard(card, onThe(project), withGadgets(prioritization));

		assertThat(card.getProject(), is(project));
		mockery.assertIsSatisfied();
	}

    private void whenISaveTheCard(Card card, Project project,
			List<Gadgets> gadgets) {
    	card.setProject(project);
    	logic.save(card, gadgets);
	}


	private List<Gadgets> withGadgets(Gadgets... gadgets) {
		return Arrays.asList(gadgets);
	}


	private void shouldSaveAGadgetOfType(final Gadgets prioritization) {

		mockery.checking(new Expectations() {
			{
				one(repository).add(with(any(prioritization.gadgetClass())));
			}
		});
	}


	private Card givenTheCardIsInThisProject(final Card card) {
        mockery.checking(new Expectations() {
            {
            	one(repository).load(card);
            	will(returnValue(card));

                one(projectRepository).load(project);
                will(returnValue(project));
            }
        });
        return card;
    }

    private void givenTheProjectIsOwnedBy(User user) {
        project.setOwner(user);
    }

	private void shouldUpdateTheCardFromRepository(final Card subcard) {
		mockery.checking(new Expectations() {
			{
				one(repository).update(subcard);
			}
		});
	}

	private void shouldNotRemoveTheCardFromRepository(final Card returned) {
		mockery.checking(new Expectations() {
			{
				never(repository).remove(returned);
			}
		});

	}

	private User anyUser() {
		User user = new User();
		user.setLogin("any");
		return user;
	}

	private void shouldRemoveTheCardFromRepository(final Card returned) {

		mockery.checking(new Expectations() {
			{
				one(repository).remove(returned);

				allowing(projectRepository).listTodoCardsFrom(project);

				allowing(repository).listSubcards(returned);
			}
		});
	}

	private void whenIRemove(Card card) {
		logic.delete(card, false);
	}

	private CardTest givenTheCard(Card card) {
		currentCard = card;
		return this;
	}

	private CardTest withName(String cardName) {
		currentCard.setName(cardName);
		return this;
	}

	private CardTest withDescription(String cardDescription) {
		currentCard.setDescription(cardDescription);
		return this;
	}

	private Card shouldLoadTheCard(final Card card) {
		final Card newcard = new Card();

		mockery.checking(new Expectations() {
			{
				allowing(projectRepository);

				one(repository).load(card);
				will(returnValue(newcard));
			}
		});
		return newcard;
	}

	private void shouldUpdateOnTheRepositoryTheCard(final Card card) {
		mockery.checking(new Expectations() {
			{
				allowing(projectRepository);
				one(repository).update(card);
				one(repository).updateGadgets(with(any(Card.class)), with(any(List.class)));
			}
		});
	}

	private String changingNameTo(String cardName) {
		return cardName;
	}

	private String changingDescriptionTo(String newDescription) {
		return newDescription;
	}

	private void whenIEditTheCard(Card card, String newName, String newDescription) {
		card.setName(newName);
		card.setDescription(newDescription);
		logic.update(card, new ArrayList<Gadgets>());
	}

	private void shouldSaveOnTheRepositoryTheCard(final Card card) {
		mockery.checking(new Expectations() {
			{
				allowing(projectRepository);

				one(repository).add(card);

			}
		});

	}

	private Project onThe(Project project) {
		return project;
	}

	private void whenISaveTheCard(Card card, Project project) {
		card.setProject(project);
		logic.save(card, new ArrayList<Gadgets>());
	}

	private Project givenAProject() {
		Project project2 = new Project();
		project2.setRepository(projectRepository);
		return project2;
	}

	private Card givenACard() {
		Card card = new Card();
		card.setProject(project);
		card.setRepository(repository);
		return card;
	}
}
