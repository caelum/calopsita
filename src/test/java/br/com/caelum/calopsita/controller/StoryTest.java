package br.com.caelum.calopsita.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Story;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.StoryRepository;

public class StoryTest {
    private Mockery mockery;
    private StoryLogic logic;
	private StoryRepository repository;
	private User currentUser;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
        repository = mockery.mock(StoryRepository.class);
        currentUser = new User();
		logic = new StoryLogic(currentUser, repository);
    }

    @After
    public void tearDown() {
        mockery.assertIsSatisfied();
    }

    @Test
	public void savingAStory() throws Exception {
		Story story = givenAStory();
		Project project = givenAProject();
	
		shouldSaveOnTheRepositoryTheStory(story);
		
		whenISaveTheStory(story, onThe(project));
		
		assertThat(story.getProject(), is(project));
		assertThat(story.getOwner(), is(currentUser));
	}

    
	private void shouldSaveOnTheRepositoryTheStory(final Story story) {
		
		mockery.checking(new Expectations() {
			{
				one(repository).save(story);
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
