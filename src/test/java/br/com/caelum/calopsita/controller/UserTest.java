package br.com.caelum.calopsita.controller;

import javax.servlet.http.HttpSession;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
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
        final User user = givenAnyUser();

        givenThatUserDoesntExist(user);

        shouldSaveTheUser(user);
        shouldPutUserInSession(user);

        whenISaveTheUser(user);
    }

    @Test
    public void signUpWithExistingUser() throws Exception {
        final User user = givenAnyUser();

        givenThatUserExists(user);

        shouldNeitherSaveNorPutInSession(user);

        whenISaveTheUser(user);

        mockery.assertIsSatisfied();
    }

    private void shouldNeitherSaveNorPutInSession(final User user) {
        mockery.checking(new Expectations() {
            {
                never(repository).add(user);
                never(session).setAttribute(User.class.getName(), user);
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
                will(returnValue(new User()));
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

    private User givenAnyUser() {
        final User user = new User();
        user.setLogin("caue");
        user.setEmail("caue.guerra@caelum.com.br");
        user.setName("caue");
        user.setPassword("caue");
        return user;
    }

}
