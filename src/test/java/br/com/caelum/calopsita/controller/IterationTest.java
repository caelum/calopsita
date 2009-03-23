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

public class IterationTest {
    private Mockery mockery;
    private IterationLogic logic;
    private IterationRepository repository;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
        repository = mockery.mock(IterationRepository.class);
        logic = new IterationLogic(repository);
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
                one(repository).add(iteration);
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
