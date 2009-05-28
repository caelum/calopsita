package br.com.caelum.calopsita.logic;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.controller.UsersController;
import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.UserRepository;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;

public class UserTest {
    private SessionUser session;
    private Mockery mockery;
    private UsersController logic;
    private UserRepository repository;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
        session = new SessionUser();
        repository = mockery.mock(UserRepository.class);
        logic = new UsersController(mockery.mock(Result.class), mockery.mock(Validator.class), repository, session);
    }

    @After
    public void tearDown() {
        mockery.assertIsSatisfied();
    }

    @Test
    public void signUpWithNewUser() throws Exception {
        final User user = givenUser("caue");

        givenThatUserDoesntExist(user);

        shouldSaveTheUser(user);

        whenISaveTheUser(user);

        assertThat(session.getUser(), is(notNullValue()));
    }

    @Test
    public void signUpWithExistingUser() throws Exception {
        final User user = givenUser("caue");

        givenThatUserExists(user);

        shouldNotSave(user);

        whenISaveTheUser(user);

        assertThat(session.getUser(), is(nullValue()));
    }

    @Test
    public void loginWithValidUser() throws Exception {
        final User user = givenUser("caue");

        givenThatUserExists(user);

        whenILoginWith(user);

        assertThat(session.getUser(), is(notNullValue()));
    }

    @Test
    public void loginWithInvalidUser() throws Exception {
        final User user = givenUser("caue");

        givenThatUserDoesntExist(user);

        shouldNotSave(user);

        whenILoginWith(user);

		assertThat(session.getUser(), is(nullValue()));

    }

    @Test
    public void logout() throws Exception {
    	session.setUser(new User());

        whenILogout();

        assertThat(session.getUser(), is(nullValue()));
    }

    @Test
    public void hashPassword() throws Exception {
        final User user = givenUser("caue");

        shouldHavePasswordHashed(user, "caue");
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
    }

    private void whenISaveTheUser(final User user) {
    	Assert.fail();
//        ValidationErrors errors = new BasicValidationErrors();
//        logic.validateSave(errors, user);
//        if (errors.size() == 0) {
            logic.save(user);
//        }
    }

    private void whenILoginWith(final User user) {
    	Assert.fail();
//        ValidationErrors errors = new BasicValidationErrors();
//        logic.validateLogin(errors, user);
//        if (errors.size() == 0) {
            logic.login(user);
//        }
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
        user.setLogin(login);
        user.setEmail(login + "@caelum.com.br");
        user.setName(login);
        user.setPassword(login);
        return user;
    }

}
