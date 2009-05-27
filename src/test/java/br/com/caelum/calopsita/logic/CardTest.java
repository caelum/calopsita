package br.com.caelum.calopsita.logic;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.CardRepository;
import br.com.caelum.calopsita.repository.ProjectRepository;

public class CardTest {
    private Mockery mockery;
    private CardLogic logic;
	private CardRepository repository;
	private User currentUser;
	private Card currentCard;
	private ProjectRepository projectRepository;
    private Project project;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
        repository = mockery.mock(CardRepository.class);
        currentUser = new User();
        currentUser.setLogin("me");
        project = new Project();

		projectRepository = mockery.mock(ProjectRepository.class);

		logic = new CardLogic(currentUser, repository, projectRepository);
    }

    @After
    public void tearDown() {
        mockery.assertIsSatisfied();
    }

    @Test
	public void savingACard() throws Exception {
    	Project project = givenAProject();
		Card card = givenACard();

		shouldSaveOnTheRepositoryTheCard(card);

		whenISaveTheCard(card, onThe(project));

		assertThat(card.getProject(), is(project));
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
	}

    @Test
	public void groupingCardsByPriority() throws Exception {
		Card card1 = givenACard(withPriority(1));
		Card card2 = givenACard(withPriority(1));
		Card card3 = givenACard(withPriority(2));
		Card card4 = givenACard(withPriority(3));
		Card card5 = givenACard(withPriority(3));

		shouldReturnTheCards(card1, card2, card3, card4, card5);
		whenIStartPrioritization();

		List<List<Card>> list = logic.getGroupedCards();

		assertThat(list.size(), is(4));
		assertThat(list.get(1), hasItem(card1));
		assertThat(list.get(1), hasItem(card2));
		assertThat(list.get(2), hasItem(card3));
		assertThat(list.get(3), hasItem(card4));
		assertThat(list.get(3), hasItem(card4));

	}

    @Test
	public void removeACardFromMyProject() throws Exception {
		Card card = givenACard();
		givenTheProjectIsOwnedBy(currentUser);

		Card returned = givenTheCardIsInThisProject(card);

		shouldRemoveTheCardFromRepository(returned);

		String status = whenIRemove(card);
		assertThat(status, is("ok"));
	}

    @Test
    public void removeACardFromOtherProjectThanMine() throws Exception {
        Card card = givenACard();
        givenTheProjectIsOwnedBy(anyUser());

        Card returned = givenTheCardIsInThisProject(card);

    	shouldNotRemoveTheCardFromRepository(returned);

    	String status = whenIRemove(card);
    	assertThat(status, is("invalid"));
    }
    @Test
    public void removeACardAndSubcards() throws Exception {
    	Card card = givenACard();
    	givenTheProjectIsOwnedBy(currentUser);

    	Card subcard = givenACard();
    	subcard.setParent(card);

    	Card returned = givenTheCardIsInThisProject(card);
    	returned.getSubcards().add(subcard);

    	shouldRemoveTheCardFromRepository(returned);
    	shouldRemoveTheCardFromRepository(subcard);

    	String status = logic.delete(card, true);
    	assertThat(status, is("ok"));
    }
    @Test
    public void removeACardButNotSubcards() throws Exception {
    	Card card = givenACard();
    	givenTheProjectIsOwnedBy(currentUser);

    	Card subCard = givenACard();
    	subCard.setParent(card);

    	Card returned = givenTheCardIsInThisProject(card);
    	returned.getSubcards().add(subCard);

    	shouldRemoveTheCardFromRepository(returned);
    	shouldUpdateTheCardFromRepository(subCard);

    	String status = logic.delete(card, false);
    	assertThat(status, is("ok"));

    	assertThat(subCard.getParent(), is(nullValue()));
    }

    private Card givenTheCardIsInThisProject(final Card card) {
        final Card returned = new Card();
        returned.setProject(this.project);

        mockery.checking(new Expectations() {
            {

                one(repository).load(card);
                will(returnValue(returned));
            }
        });
        return returned;
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
			}
		});
	}

	private String whenIRemove(Card card) {
		return logic.delete(card, false);
	}

	private void shouldReturnTheCards(final Card... cards) {
		mockery.checking(new Expectations() {
			{
				one(projectRepository).listCardsFrom(with(any(Project.class)));
				will(returnValue(Arrays.asList(cards)));

				allowing(projectRepository);
			}
		});
	}

	private void whenIStartPrioritization() {
		logic.prioritization(givenAProject());
	}

	private Card givenACard(int priority) {
		Card card = givenACard();
		card.setPriority(priority);
		return card;
	}

	private int withPriority(int i) {
		return i;
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
		logic.update(card);
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
		logic.save(card, project);
	}

	private Project givenAProject() {
		return new Project();
	}

	private Card givenACard() {
		return new Card();
	}
}
