package br.com.caelum.calopsita.logic;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Story;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.IterationRepository;
import br.com.caelum.calopsita.repository.StoryRepository;

public class IterationTest {
    private Mockery mockery;
    private IterationLogic logic;
    private IterationRepository iterationRepository;
    private StoryRepository storyRepository;
    private User currentUser;
    private Project project;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
        iterationRepository = mockery.mock(IterationRepository.class);
        storyRepository = mockery.mock(StoryRepository.class);
        
        currentUser = new User();
        currentUser.setLogin("me");
        project = new Project();

        logic = new IterationLogic(currentUser, iterationRepository, storyRepository);
        
    }

    @After
    public void tearDown() {
        mockery.assertIsSatisfied();
    }
    
    @Test
    public void savingAnIteration() throws Exception {
        Iteration iteration = givenAnIteration();
        Project project = givenAProject();
    
        shouldSaveOnTheRepositoryTheIteration(iteration);
        
        whenISaveTheIteration(iteration, onThe(project));
        
        assertThat(iteration.getProject(), is(project));
    }

    @Test
	public void addingAStoryInAnIteration() throws Exception {
		Iteration iteration = givenAnIteration();
		Story story = givenAStory();
		
		shouldUpdateTheStory(story);
		
		whenIAddTheStoryToIteration(story, iteration);
		
		assertThat(story.getIteration(), is(iteration));
	}
    @Test
    public void removingAStoryOfAnIteration() throws Exception {
    	Iteration iteration = givenAnIteration();
    	Story story = givenAStory();
    	
    	Story loaded = givenLoadedStoryContainsIteration(story, iteration);
    	
    	whenIRemoveTheStoryOfIteration(story, iteration);

    	assertThat(loaded.getIteration(), is(nullValue()));
    }
    
    @Test
    public void removeAnIterationFromOtherProject() throws Exception {
        Iteration iteration = givenAnIteration();
        givenTheProjectIsOwnedBy(anyUser());
        Iteration returned = givenTheIterationIsInThisProject(iteration);
        
        shouldNotRemoveTheIterationFromRepository(returned);
        
        String status = whenIRemove(iteration);
        assertThat(status, is("invalid"));
    }
    
    @Test
    public void removeAnIterationFromMyProject() throws Exception {
        Iteration iteration = givenAnIteration();
        givenTheProjectIsOwnedBy(currentUser);

        Story story = givenAStory();
        Iteration returnedIteration = givenTheIterationIsInThisProject(iteration);
        
        givenTheIterationHasThisStory(story, returnedIteration);
        
        shouldUpdateTheStory(story);
        shouldRemoveTheIterationFromRepository(returnedIteration);
        
        String status = whenIRemove(iteration);
        assertThat(status, is("ok"));
    }
    
    private void givenTheIterationHasThisStory(Story story, Iteration returnedIteration) {
        returnedIteration.addStory(story);
        story.setIteration(returnedIteration);
    }

    private void givenTheProjectIsOwnedBy(User user) {
	    project.setOwner(user);
    }

    private void shouldNotRemoveTheIterationFromRepository(final Iteration returned) {
	    mockery.checking(new Expectations() {
            {
                never(iterationRepository).remove(returned);
            }
        });
    }

    private User anyUser() {
	    User user = new User();
	    user.setName("any name");
	    return user;
    }

    private String whenIRemove(Iteration iteration) {
	    return logic.delete(iteration);
    }

    private void shouldRemoveTheIterationFromRepository(final Iteration returned) {
	    mockery.checking(new Expectations() {
            {
                one(iterationRepository).remove(returned);
            }
        });
    }

    private Iteration givenTheIterationIsInThisProject(final Iteration iteration) {
	    final Iteration returned = new Iteration();
        returned.setProject(this.project);
        
        mockery.checking(new Expectations() {
            {
                
                one(iterationRepository).load(iteration);
                will(returnValue(returned));
            }
        });
        return returned;
    }

    private void whenIRemoveTheStoryOfIteration(Story story, Iteration iteration) {
		logic.removeStories(iteration, Arrays.asList(story));
	}

	private Story givenLoadedStoryContainsIteration(final Story story, final Iteration iteration) {
		final Story loaded = new Story();
		
		mockery.checking(new Expectations() {
			{
				loaded.setIteration(iteration);
				
				one(storyRepository).load(story);
				will(returnValue(loaded));
				
				one(storyRepository).update(loaded);
			}
		});
		return loaded;
	}

	private void shouldUpdateTheStory(final Story story) {
    	
		mockery.checking(new Expectations() {
			{
				one(storyRepository).update(story);
				
				one(storyRepository).load(story);
				will(returnValue(story));
			}
		});
	}

	private void whenIAddTheStoryToIteration(Story story, Iteration iteration) {
    	logic.updateStories(iteration, Arrays.asList(story));
	}

	private Story givenAStory() {
		return new Story();
	}

	private void shouldSaveOnTheRepositoryTheIteration(final Iteration iteration) {
        
        mockery.checking(new Expectations() {
            {
                one(iterationRepository).add(iteration);
            }
        });
    }

    private Project onThe(Project project) {
        return project;
    }

    private void whenISaveTheIteration(Iteration iteration, Project project) {
        logic.save(iteration, project);
    }

    private Project givenAProject() {
        return new Project();
    }

    private Iteration givenAnIteration() {
        return new Iteration();
    }
}
