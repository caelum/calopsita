package br.com.caelum.calopsita.logic;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.controller.ProjectsController;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.calopsita.repository.UserRepository;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;

public class ProjectTest {
    private Mockery mockery;
    private ProjectsController logic;
    private ProjectRepository repository;
	private User currentUser;
	private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
        repository = mockery.mock(ProjectRepository.class);
        currentUser = currentUser();
        userRepository = mockery.mock(UserRepository.class);
		logic = new ProjectsController(mockery.mock(Validator.class), mockery.mock(Result.class), repository, userRepository, currentUser);
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
    public void saveAProject() throws Exception {
    	Project project = givenAProject();

    	shouldSaveTheProjectOnTheRepository(project);

    	whenISaveTheProject(project);

    	assertThat(project.getOwner(), is(currentUser));
    }
    @Test
    public void addingColaboratorsToAProject() throws Exception {
    	Project project = givenAProject();

    	User user = givenAUser();

    	shouldReloadAndUpdateTheProject(project);

    	whenIAddTheUserToTheProject(user, project);

    	thenTheProjectWillContainTheUserAsColaborator(project, user);
    }

    @Test
	public void removingAProjectOwnedByMe() throws Exception {
		Project project = givenAProject();

		Project loaded = givenProjectIsOwnedBy(project, currentUser);

		shouldRemoveFromRepository(loaded);
		
		String result = null;
		whenIRemoveTheProject(project);
		
		assertThat(result, is("ok"));
	}
    @Test
    public void removingAProjectOwnedByOthers() throws Exception {
    	Project project = givenAProject();

    	Project loaded = givenProjectIsOwnedBy(project, givenAUser());

    	shouldNotRemoveFromRepository(loaded);
    	
    	String result = null;
    	whenIRemoveTheProject(project);
    	
    	assertThat(result, is("invalid"));
    }

    @Test
    public void editingAProjectOwnedByMe() throws Exception {
    	Project project = givenAProject();
    	project.setDescription("New description");

    	Project loaded = givenProjectIsOwnedBy(project, currentUser);

    	String result = whenIEditTheProject(project);

    	assertThat(result, is("ok"));
    	assertThat(loaded.getDescription(), is("New description"));
    }

	@Test
    public void editingAProjectOwnedByOthers() throws Exception {
    	Project project = givenAProject();
    	project.setDescription("Anything");
    	Project loaded = givenProjectIsOwnedBy(project, givenAUser());

    	String result = whenIEditTheProject(project);

    	assertThat(result, is("invalid"));

    	assertThat(loaded.getDescription(), is(not("Anything")));
    }

	private String whenIEditTheProject(Project project) {
		return logic.update(project);
	}

	private void shouldNotRemoveFromRepository(final Project project) {
		mockery.checking(new Expectations() {
			{
				never(repository).remove(project);
			}
		});

	}

	private Project givenProjectIsOwnedBy(final Project project, final User user) {

		final Project result = new Project();
		result.setOwner(user);
		mockery.checking(new Expectations() {
			{
				one(repository).load(project);
				will(returnValue(result));
			}
		});
		return result;
	}

	private void shouldRemoveFromRepository(final Project project) {

		mockery.checking(new Expectations() {
			{
				one(repository).remove(project);
			}
		});
	}

	private void whenIRemoveTheProject(Project project) {
		logic.delete(project);
	}

	private void shouldReloadAndUpdateTheProject(final Project project) {

		mockery.checking(new Expectations() {
			{
				one(repository).get(project.getId());
				will(returnValue(project));

				one(repository).update(project);
			}
		});
	}

	private void thenTheProjectWillContainTheUserAsColaborator(Project project, User user) {
		assertThat(project.getColaborators(), hasItem(user));
	}

	private void whenIAddTheUserToTheProject(User user, Project project) {
		logic.addColaborator(project, user);
	}


	private User givenAUser() {
		return new User();
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
		Assert.fail();
//        assertThat(logic.getProjects(), is(notNullValue()));
//        assertThat(logic.getProjects().size(), is(1));
//        assertThat(logic.getProjects(), hasItem(project));
    }

    private void whenIListProjects() {
        logic.list();
    }

    private Project givenThatOnlyExistsOneProjectForCurrentUser() {
        final Project project = new Project();
        mockery.checking(new Expectations() {
            {
                one(repository).listAllFrom(currentUser);
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
