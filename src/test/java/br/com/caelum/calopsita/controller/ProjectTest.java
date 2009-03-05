package br.com.caelum.calopsita.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;

public class ProjectTest {
    private Mockery mockery;
    private ProjectLogic logic;
    private ProjectRepository repository;
    private HttpSession session;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
        session = mockery.mock(HttpSession.class);
        repository = mockery.mock(ProjectRepository.class);
        logic = new ProjectLogic(repository, session);
    }

    @After
    public void tearDown() {
        mockery.assertIsSatisfied();
    }

    @Test
    public void listingAProject() throws Exception {
        final User user = givenUser("caue");
        Project project = givenThatOnlyExistsOneProjectForUser(user);
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

    private Project givenThatOnlyExistsOneProjectForUser(final User user) {
        final Project project = new Project();
        mockery.checking(new Expectations() {
            {
                one(session).getAttribute(User.class.getName());
                will(returnValue(user));
                one(repository).listAllFromOwner(user);
                will(returnValue(Collections.singletonList(project)));
            }
        });
        return project;
    }

    private User givenUser(String login) {
        final User user = new User();
        user.setLogin(login);
        user.setEmail(login + "@caelum.com.br");
        user.setName(login);
        user.setPassword(login);
        return user;
    }
}
