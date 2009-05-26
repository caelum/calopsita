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
	private Card currentStory;
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
	public void savingAStory() throws Exception {
    	Project project = givenAProject();
		Card story = givenAStory();

		shouldSaveOnTheRepositoryTheStory(story);

		whenISaveTheStory(story, onThe(project));

		assertThat(story.getProject(), is(project));
	}

    @Test
	public void editingAStorysDescription() throws Exception {
    	Card story = givenAStory();
    	givenTheStory(story).withName("Huck Finn")
    						.withDescription("He is Tom Sawyer's best mate.");

    	Card loadedStory = shouldLoadTheStory(story);
    	shouldUpdateOnTheRepositoryTheStory(loadedStory);

    	whenIEditTheStory(story, changingNameTo("Huckleberry Finn"), changingDescriptionTo("He has a drunk father."));

		assertThat(loadedStory.getName(), is("Huckleberry Finn"));
		assertThat(loadedStory.getDescription(), is("He has a drunk father."));
	}

    @Test
	public void groupingStoriesByPriority() throws Exception {
		Card story1 = givenAStory(withPriority(1));
		Card story2 = givenAStory(withPriority(1));
		Card story3 = givenAStory(withPriority(2));
		Card story4 = givenAStory(withPriority(3));
		Card story5 = givenAStory(withPriority(3));

		shouldReturnTheStories(story1, story2, story3, story4, story5);
		whenIStartPrioritization();

		List<List<Card>> list = logic.getGroupedCards();

		assertThat(list.size(), is(4));
		assertThat(list.get(1), hasItem(story1));
		assertThat(list.get(1), hasItem(story2));
		assertThat(list.get(2), hasItem(story3));
		assertThat(list.get(3), hasItem(story4));
		assertThat(list.get(3), hasItem(story4));

	}

    @Test
	public void removeAStoryFromMyProject() throws Exception {
		Card story = givenAStory();
		givenTheProjectIsOwnedBy(currentUser);

		Card returned = givenTheStoryIsInThisProject(story);

		shouldRemoveTheStoryFromRepository(returned);

		String status = whenIRemove(story);
		assertThat(status, is("ok"));
	}

    @Test
    public void removeAStoryFromOtherProjectThanMine() throws Exception {
        Card story = givenAStory();
        givenTheProjectIsOwnedBy(anyUser());

        Card returned = givenTheStoryIsInThisProject(story);

    	shouldNotRemoveTheStoryFromRepository(returned);

    	String status = whenIRemove(story);
    	assertThat(status, is("invalid"));
    }
    @Test
    public void removeAStoryAndSubstories() throws Exception {
    	Card story = givenAStory();
    	givenTheProjectIsOwnedBy(currentUser);

    	Card substory = givenAStory();
    	substory.setParent(story);

    	Card returned = givenTheStoryIsInThisProject(story);
    	returned.getSubcards().add(substory);

    	shouldRemoveTheStoryFromRepository(returned);
    	shouldRemoveTheStoryFromRepository(substory);

    	String status = logic.delete(story, true);
    	assertThat(status, is("ok"));
    }
    @Test
    public void removeAStoryButNotSubstories() throws Exception {
    	Card story = givenAStory();
    	givenTheProjectIsOwnedBy(currentUser);

    	Card substory = givenAStory();
    	substory.setParent(story);

    	Card returned = givenTheStoryIsInThisProject(story);
    	returned.getSubcards().add(substory);

    	shouldRemoveTheStoryFromRepository(returned);
    	shouldUpdateTheStoryFromRepository(substory);

    	String status = logic.delete(story, false);
    	assertThat(status, is("ok"));

    	assertThat(substory.getParent(), is(nullValue()));
    }

    private Card givenTheStoryIsInThisProject(final Card story) {
        final Card returned = new Card();
        returned.setProject(this.project);

        mockery.checking(new Expectations() {
            {

                one(repository).load(story);
                will(returnValue(returned));
            }
        });
        return returned;
    }

    private void givenTheProjectIsOwnedBy(User user) {
        project.setOwner(user);
    }

	private void shouldUpdateTheStoryFromRepository(final Card substory) {
		mockery.checking(new Expectations() {
			{
				one(repository).update(substory);
			}
		});
	}

	private void shouldNotRemoveTheStoryFromRepository(final Card returned) {
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

	private void shouldRemoveTheStoryFromRepository(final Card returned) {

		mockery.checking(new Expectations() {
			{
				one(repository).remove(returned);
			}
		});
	}

	private String whenIRemove(Card story) {
		return logic.delete(story, false);
	}

	private void shouldReturnTheStories(final Card... stories) {
		mockery.checking(new Expectations() {
			{
				one(projectRepository).listCardsFrom(with(any(Project.class)));
				will(returnValue(Arrays.asList(stories)));

				allowing(projectRepository);
			}
		});
	}

	private void whenIStartPrioritization() {
		logic.prioritization(givenAProject());
	}

	private Card givenAStory(int priority) {
		Card story = givenAStory();
		story.setPriority(priority);
		return story;
	}

	private int withPriority(int i) {
		return i;
	}

	private CardTest givenTheStory(Card story) {
		currentStory = story;
		return this;
	}

	private CardTest withName(String storyName) {
		currentStory.setName(storyName);
		return this;
	}

	private CardTest withDescription(String storyDescription) {
		currentStory.setDescription(storyDescription);
		return this;
	}

	private Card shouldLoadTheStory(final Card story) {
		final Card newStory = new Card();

		mockery.checking(new Expectations() {
			{
				allowing(projectRepository);

				one(repository).load(story);
				will(returnValue(newStory));
			}
		});
		return newStory;
	}

	private void shouldUpdateOnTheRepositoryTheStory(final Card story) {
		mockery.checking(new Expectations() {
			{
				allowing(projectRepository);
				one(repository).update(story);
			}
		});
	}

	private String changingNameTo(String storyName) {
		return storyName;
	}

	private String changingDescriptionTo(String newDescription) {
		return newDescription;
	}

	private void whenIEditTheStory(Card story, String newName, String newDescription) {
		story.setName(newName);
		story.setDescription(newDescription);
		logic.update(story);
	}

	private void shouldSaveOnTheRepositoryTheStory(final Card story) {
		mockery.checking(new Expectations() {
			{
				allowing(projectRepository);

				one(repository).add(story);
			}
		});

	}

	private Project onThe(Project project) {
		return project;
	}

	private void whenISaveTheStory(Card story, Project project) {
		logic.save(story, project);
	}

	private Project givenAProject() {
		return new Project();
	}

	private Card givenAStory() {
		return new Card();
	}
}
