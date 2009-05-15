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

		String hql = "from User u where u != :owner";
		if (!project.getColaborators().isEmpty()) {
			hql += " and u not in (:colaborators)";
		}
		Query query = session.createQuery(hql);
		
		if (!project.getColaborators().isEmpty()) {
			query.setParameterList("colaborators", project.getColaborators());
		}
		return query.setParameter("owner", project.getOwner()).list();
	}

}
