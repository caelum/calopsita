package br.com.caelum.calopsita.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.IterationRepository;
import br.com.caelum.calopsita.repository.StoryRepository;

public class IterationTest {
    private Mockery mockery;
    private IterationLogic logic;
    private IterationRepository iterationRepository;
    private StoryRepository storyRepository;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
        iterationRepository = mockery.mock(IterationRepository.class);
        storyRepository = mockery.mock(StoryRepository.class);
        logic = new IterationLogic(iterationRepository, storyRepository);
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
