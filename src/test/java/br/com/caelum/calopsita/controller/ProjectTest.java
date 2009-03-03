package br.com.caelum.calopsita.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Collections;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.ProjectRepository;

public class ProjectTest {
    private Mockery mockery;
    private ProjectLogic logic;
    private ProjectRepository repository;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
        repository = mockery.mock(ProjectRepository.class);
        logic = new ProjectLogic(repository);
    }

    @After
    public void tearDown() {
        mockery.assertIsSatisfied();
    }

    @Test
	public void listingAProject() throws Exception {
		Project project = givenThatOnlyExistsOneProject();
		whenIListProjects();
		thenTheLogicShouldExposeOnlyTheProject(project);
	}

	private void thenTheLogicShouldExposeOnlyTheProject(Project project) {
		Assert.assertThat(logic.getProjects(), is(notNullValue()));
		Assert.assertThat(logic.getProjects().size(), is(1));
		Assert.assertThat(logic.getProjects(), hasItem(project));
	}

	private void whenIListProjects() {
		logic.list();
	}

	private Project givenThatOnlyExistsOneProject() {
		final Project project = new Project();
		mockery.checking(new Expectations() {
			{
				one(repository).listAll();
				will(returnValue(Collections.singletonList(project)));
			}
		});
		return project;
	}
}
