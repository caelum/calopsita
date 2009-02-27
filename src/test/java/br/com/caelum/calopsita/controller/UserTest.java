package br.com.caelum.calopsita.controller;

import javax.servlet.http.HttpSession;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.vraptor.validator.BasicValidationErrors;
import org.vraptor.validator.ValidationErrors;

import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.UserRepository;

public class UserTest {
    private HttpSession session;
    private Mockery mockery;
    private UserLogic logic;
    private UserRepository repository;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
        session = mockery.mock(HttpSession.class);
        repository = mockery.mock(UserRepository.class);
        logic = new UserLogic(repository, session);
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
        shouldPutUserInSession(user);

        whenISaveTheUser(user);
    }

    @Test
    public void signUpWithExistingUser() throws Exception {
        final User user = givenUser("caue");

        givenThatUserExists(user);

        shouldNeitherSaveNorPutInSession(user);

        whenISaveTheUser(user);
    }

    @Test
    public void loginWithValidUser() throws Exception {
        final User user = givenUser("caue");

        givenThatUserExists(user);

        shouldPutUserInSession(user);

        whenILoginWith(user);
    }

    @Test
    public void loginWithInvalidUser() throws Exception {
        final User user = givenUser("caue");

        givenThatUserDoesntExist(user);

        shouldNeitherSaveNorPutInSession(user);

        whenILoginWith(user);
    }

    @Test
    public void logout() throws Exception {
        shouldRemoveFromSession();

        whenILogout();
    }

    @Test
    public void hashPassword() throws Exception {
        final User user = givenUser("caue");

        shouldHavePasswordHashed(user, "caue");
    }

    private void shouldHavePasswordHashed(User user, String login) {
        Assert.assertNotSame(user.getPassword(), login);
    }

    private void shouldNeitherSaveNorPutInSession(final User user) {
        mockery.checking(new Expectations() {
            {
                never(repository).add(user);
                never(session).setAttribute(User.class.getName(), user);
                never(session).setAttribute("currentUser", user);
            }
        });

    }

    private void shouldRemoveFromSession() {
        mockery.checking(new Expectations() {
            {
                one(session).removeAttribute(User.class.getName());
                one(session).removeAttribute("currentUser");
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
        ValidationErrors errors = new BasicValidationErrors();
        logic.validateSave(errors, user);
        if (errors.size() == 0) {
            logic.save(user);
        }
    }

    private void whenILoginWith(final User user) {
        ValidationErrors errors = new BasicValidationErrors();
        logic.validateLogin(errors, user);
        if (errors.size() == 0) {
            logic.login(user);
        }
    }

    private void whenILogout() {
        logic.logout();
    }

    private void shouldPutUserInSession(final User user) {
        mockery.checking(new Expectations() {
            {
                one(session).setAttribute(User.class.getName(), user);
                one(session).setAttribute("currentUser", user);
            }
        });
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
