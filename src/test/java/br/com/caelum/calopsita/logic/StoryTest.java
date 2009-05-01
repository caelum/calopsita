package br.com.caelum.calopsita.logic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.logic.StoryLogic;
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

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
        repository = mockery.mock(StoryRepository.class);
        currentUser = new User();
		final ProjectRepository projectRepository = mockery.mock(ProjectRepository.class);
		
		mockery.checking(new Expectations() {
			{
				allowing(projectRepository);
			}
		});
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
				one(repository).load(story);
				will(returnValue(newStory));
			}
		});
		return newStory;
	}
	
	private void shouldUpdateOnTheRepositoryTheStory(final Story story) {
		mockery.checking(new Expectations() {
			{
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
