package br.com.caelum.calopsita.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;

public class ProjectTest {
    private Mockery mockery;
    private ProjectLogic logic;
    private ProjectRepository repository;
	private User user;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
        repository = mockery.mock(ProjectRepository.class);
        user = currentUser();
        logic = new ProjectLogic(repository, user);
    }

    @After
    public void tearDown() {
        mockery.assertIsSatisfied();
    }

    @Test
    public void listingAProject() throws Exception {
        Project project = givenThatOnlyExistsOneProjectForCurrentUser();
        whenIListProjects();
        thenTheLogicShouldExposeOnlyTheProject(project);
    }
    @Test
    public void savAProject() throws Exception {
    	Project project = givenAProject();

    	shouldSaveTheProjectOnTheRepository(project);
    	
    	whenISaveTheProject(project);
    	
    	assertThat(project.getOwner(), is(user));
    }

    private void whenISaveTheProject(Project project) {
    	logic.save(project);
	}

	private void shouldSaveTheProjectOnTheRepository(final Project project) {
    	mockery.checking(new Expectations() {
    		{
    			one(repository).add(project);
    		}
    	});
	}

	private Project givenAProject() {
		return new Project();
	}

	private void thenTheLogicShouldExposeOnlyTheProject(Project project) {
        assertThat(logic.getProjects(), is(notNullValue()));
        assertThat(logic.getProjects().size(), is(1));
        assertThat(logic.getProjects(), hasItem(project));
    }

    private void whenIListProjects() {
        logic.list();
    }

    private Project givenThatOnlyExistsOneProjectForCurrentUser() {
        final Project project = new Project();
        mockery.checking(new Expectations() {
            {
                one(repository).listAllFromOwner(user);
                will(returnValue(Collections.singletonList(project)));
            }
        });
        return project;
    }

    private User currentUser() {
        final User user = new User();
        String login = "caue";
        user.setLogin(login);
        user.setEmail(login + "@caelum.com.br");
        user.setName(login);
        user.setPassword(login);
        return user;
    }
}
