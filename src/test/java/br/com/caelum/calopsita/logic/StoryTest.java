package br.com.caelum.calopsita.logic;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Story;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.calopsita.repository.StoryRepository;

public class StoryTest {
    private Mockery mockery;
    private StoryLogic logic;
	private StoryRepository repository;
	private User currentUser;
	private Story currentStory;
	private ProjectRepository projectRepository;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
        repository = mockery.mock(StoryRepository.class);
        currentUser = new User();
        currentUser.setLogin("me");
        
		projectRepository = mockery.mock(ProjectRepository.class);

		logic = new StoryLogic(currentUser, repository, projectRepository);
    }

    @After
    public void tearDown() {
        mockery.assertIsSatisfied();
    }

    @Test
	public void savingAStory() throws Exception {
    	Project project = givenAProject();
		Story story = givenAStory();
	
		shouldSaveOnTheRepositoryTheStory(story);
		
		whenISaveTheStory(story, onThe(project));
		
		assertThat(story.getProject(), is(project));
		assertThat(story.getOwner(), is(currentUser));
	}

    @Test
	public void editingAStorysDescription() throws Exception {
    	Story story = givenAStory();
    	givenTheStory(story).withName("Huck Finn")
    						.withDescription("He is Tom Sawyer's best mate.");

    	Story loadedStory = shouldLoadTheStory(story);
    	shouldUpdateOnTheRepositoryTheStory(loadedStory);
		
    	whenIEditTheStory(story, changingNameTo("Huckleberry Finn"), changingDescriptionTo("He has a drunk father."));
		
		assertThat(loadedStory.getName(), is("Huckleberry Finn"));
		assertThat(loadedStory.getDescription(), is("He has a drunk father."));
	}
    
    @Test
	public void groupingStoriesByPriority() throws Exception {
		Story story1 = givenAStory(withPriority(1));
		Story story2 = givenAStory(withPriority(1));
		Story story3 = givenAStory(withPriority(2));
		Story story4 = givenAStory(withPriority(3));
		Story story5 = givenAStory(withPriority(3));
		
		shouldReturnTheStories(story1, story2, story3, story4, story5);
		whenIStartPrioritization();
		
		List<List<Story>> list = logic.getGroupedStories();
		
		assertThat(list.size(), is(4));
		assertThat(list.get(1), hasItem(story1));
		assertThat(list.get(1), hasItem(story2));
		assertThat(list.get(2), hasItem(story3));
		assertThat(list.get(3), hasItem(story4));
		assertThat(list.get(3), hasItem(story4));
		
	}
    
    @Test
	public void removeAStoryOwnedByMe() throws Exception {
		Story story = givenAStory();
		
		Story returned = givenTheStoryIsOwnedBy(story, currentUser);
		
		shouldRemoveTheStoryFromRepository(returned);
		
		String status = whenIRemove(story);
		assertThat(status, is("ok"));
	}
    @Test
    public void removeAStoryOwnedByOthers() throws Exception {
    	Story story = givenAStory();
    	
    	Story returned = givenTheStoryIsOwnedBy(story, anyUser());
    	
    	shouldNotRemoveTheStoryFromRepository(returned);
    	
    	String status = whenIRemove(story);
    	assertThat(status, is("invalid"));
    }

	private void shouldNotRemoveTheStoryFromRepository(final Story returned) {
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

	private void shouldRemoveTheStoryFromRepository(final Story returned) {
		
		mockery.checking(new Expectations() {
			{
				one(repository).remove(returned);
			}
		});
	}

	private Story givenTheStoryIsOwnedBy(final Story story, final User user) {
		
		final Story returned = new Story();
		returned.setOwner(user);
		
		mockery.checking(new Expectations() {
			{
				
				one(repository).load(story);
				will(returnValue(returned));
			}
		});
		return returned;
	}

	private String whenIRemove(Story story) {
		return logic.delete(story, false);
	}

	private void shouldReturnTheStories(final Story... stories) {
		mockery.checking(new Expectations() {
			{
				one(projectRepository).listStoriesFrom(with(any(Project.class)));
				will(returnValue(Arrays.asList(stories)));
				
				allowing(projectRepository);
			}
		});
	}

	private void whenIStartPrioritization() {
		logic.prioritization(givenAProject());
	}

	private Story givenAStory(int priority) {
		Story story = givenAStory();
		story.setPriority(priority);
		return story;
	}

	private int withPriority(int i) {
		return i;
	}

	private StoryTest givenTheStory(Story story) {
		currentStory = story;
		return this;
	}

	private StoryTest withName(String storyName) {
		currentStory.setName(storyName);
		return this;
	}

	private StoryTest withDescription(String storyDescription) {
		currentStory.setDescription(storyDescription);
		return this;
	}
	
	private Story shouldLoadTheStory(final Story story) {
		final Story newStory = new Story();
		
		mockery.checking(new Expectations() {
			{
				allowing(projectRepository);
				
				one(repository).load(story);
				will(returnValue(newStory));
			}
		});
		return newStory;
	}
	
	private void shouldUpdateOnTheRepositoryTheStory(final Story story) {
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
	
	private void whenIEditTheStory(Story story, String newName, String newDescription) {
		story.setName(newName);
		story.setDescription(newDescription);
		logic.update(story);
	}
	
	private void shouldSaveOnTheRepositoryTheStory(final Story story) {
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

	private void whenISaveTheStory(Story story, Project project) {
		logic.save(story, project);
	}

	private Project givenAProject() {
		return new Project();
	}

	private Story givenAStory() {
		return new Story();
	}
}
