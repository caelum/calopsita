package br.com.caelum.calopsita.persistence.dao;

import java.util.List;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.UserRepository;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class UserDao implements UserRepository {

    private final Session session;

    public UserDao(Session session) {
        this.session = session;
    }

    public void add(User user) {
        this.session.save(user);
    }

    public void update(User user) {
        this.session.merge(user);
    }

    public void remove(User user) {
    }

    public User find(String login) {
        return (User) this.session.createQuery("from User where login = :login").setParameter("login", login).uniqueResult();
    }

    public List<User> listAll() {
    	return session.createQuery("from User").list();
    }

}
