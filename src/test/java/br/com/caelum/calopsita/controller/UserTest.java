package br.com.caelum.calopsita.controller;

import javax.servlet.http.HttpSession;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

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

    @Test
    public void login() throws Exception {
        final User user = new User();
        user.setLogin("caue");
        user.setEmail("caue.guerra@caelum.com.br");
        user.setName("caue");
        user.setPassword("caue");

        mockery.checking(new Expectations() {
            {
                one(repository).add(user);
                one(session).setAttribute(User.class.getName(), user);
            }
        });

        logic.save(user);

        mockery.assertIsSatisfied();
    }
}
