package br.com.caelum.calopsita.persistence.dao;

import java.util.List;

import br.com.caelum.calopsita.model.Project;

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
    public User find(String login) {
        return (User) this.session.createQuery("from User where login = :login").setParameter("login", login).uniqueResult();
    }
    @Override
    public List<User> listAll() {
    	return session.createQuery("from User").list();
    }

	@Override
	public List<User> listUnrelatedUsers(Project project) {
		return this.session.createQuery("from User u where u not in (:colaborators) and u != :owner")
							.setParameterList("colaborators", project.getColaborators())
							.setParameter("owner", project.getOwner()).list();
	}

}
