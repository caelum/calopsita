package br.com.caelum.calopsita.persistence.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.UserRepository;
import br.com.caelum.vraptor.ioc.Component;

@Component
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

		String hql = "select u from User u, Project p where p = :project " +
				"and u != p.owner and u not in elements (p.colaborators)";
		Query query = session.createQuery(hql);
		query.setParameter("project", project);
		return query.list();
	}

}
