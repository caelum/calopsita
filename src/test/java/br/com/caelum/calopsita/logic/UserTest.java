package br.com.caelum.calopsita.logic;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.controller.UsersController;
import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.mocks.MockHttpSession;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.UserRepository;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationError;

public class UserTest {
    private SessionUser sessionUser;
    private Mockery mockery;
    private UsersController logic;
    private UserRepository repository;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
		sessionUser = new SessionUser(new MockHttpSession());
        repository = mockery.mock(UserRepository.class);

        logic = new UsersController(new MockResult(), new MockValidator(), sessionUser);
    }


    @Test
    public void aNewUserIsNewbie() throws Exception {
    	final User user = givenUser("caue");

    	givenThatUserDoesntExist(user);

    	shouldSaveTheUser(user);

    	whenISaveTheUser(user);

    	assertThat(sessionUser.getUser(), is(notNullValue()));
    	assertThat(sessionUser.getUser().isNewbie(), is(true));
    	mockery.assertIsSatisfied();
    }

    @Test
	public void toggleNewbie() throws Exception {
    	final User user = givenUser("caue");

    	givenThatUserExists(user);

    	assertThat(sessionUser.getUser().isNewbie(), is(false));

    	whenIToggleNewbie();

    	assertThat(sessionUser.getUser().isNewbie(), is(true));

	}

	@Test
    public void signUpWithNewUser() throws Exception {
        final User user = givenUser("caue");

        givenThatUserDoesntExist(user);

        shouldSaveTheUser(user);

        whenISaveTheUser(user);

        assertThat(sessionUser.getUser(), is(notNullValue()));
        mockery.assertIsSatisfied();
    }

    @Test(expected=ValidationError.class)
    public void signUpWithExistingUser() throws Exception {
        final User user = givenUser("caue");

        givenThatUserExists(user);

        shouldNotSave(user);

        whenISaveTheUser(user);

        assertThat(sessionUser.getUser(), is(nullValue()));
        mockery.assertIsSatisfied();
    }

    @Test
    public void loginWithValidUser() throws Exception {
        final User user = givenUser("caue");

        givenThatUserExists(user);

        whenILoginWith(user);

        assertThat(sessionUser.getUser(), is(user));
        mockery.assertIsSatisfied();
    }



	@Test(expected=ValidationError.class)
    public void loginWithInvalidUser() throws Exception {
        final User user = givenUser("caue");

        givenThatUserDoesntExist(user);

        shouldNotSave(user);

        whenILoginWith(user);

		assertThat(sessionUser.getUser(), is(nullValue()));

		mockery.assertIsSatisfied();
    }

    @Test
    public void logout() throws Exception {
    	givenThereIsAnUserOnSession();

    	whenILogout();

        assertThat(sessionUser.getUser(), is(nullValue()));

        mockery.assertIsSatisfied();
    }


	private void givenThereIsAnUserOnSession() {
		sessionUser.setUser(new User());
	}



	@Test
    public void hashPassword() throws Exception {
        final User user = givenUser("caue");

        shouldHavePasswordHashed(user, "caue");
        mockery.assertIsSatisfied();
    }

    private void whenIToggleNewbie() {
    	logic.toggleNewbie();
	}

    private void shouldHavePasswordHashed(User user, String login) {
        Assert.assertNotSame(user.getPassword(), login);
    }

    private void shouldNotSave(final User user) {
        mockery.checking(new Expectations() {
            {
                never(repository).add(user);
            }
        });

    }

    private void givenThatUserDoesntExist(final User user) {
        mockery.checking(new Expectations() {
            {
                one(repository).find(user.getLogin());
                will(returnValue(null));
            }
        });
    }

    private void givenThatUserExists(final User user) {
        mockery.checking(new Expectations() {
            {
                one(repository).find(user.getLogin());
                will(returnValue(user));
            }
        });
        sessionUser.setUser(user);
    }

    private void whenISaveTheUser(final User user) {
    	logic.save(user);
    }

    private void whenILoginWith(final User user) {
    	logic.login(user);
    }

    private void whenILogout() {
        logic.logout();
    }

    private void shouldSaveTheUser(final User user) {
        mockery.checking(new Expectations() {
            {
                one(repository).add(user);
            }
        });
    }

    private User givenUser(String login) {
        final User user = new User();
        user.setRepository(repository);
        user.setLogin(login);
        user.setEmail(login + "@caelum.com.br");
        user.setName(login);
        user.setPassword(login);
        return user;
    }

}
