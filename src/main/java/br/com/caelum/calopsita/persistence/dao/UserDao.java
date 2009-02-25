package br.com.caelum.calopsita.persistence.dao;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.UserRepository;

public class UserDao implements UserRepository {

    private final Session session;

    public UserDao(Session session) {
        this.session = session;
    }

    @Override
    public void add(User user) {
        this.session.save(user);
    }

    @Override
    public void update(User user) {
        this.session.merge(user);
    }

    @Override
    public void remove(User user) {
    }

    @Override
    public void find(String login) {
        this.session.load(User.class, login);
    }

}
