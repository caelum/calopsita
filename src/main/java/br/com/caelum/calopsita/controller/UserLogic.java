package br.com.caelum.calopsita.controller;

import javax.servlet.http.HttpSession;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.hibernate.HibernateInterceptor;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.UserRepository;

@Component
@InterceptedBy(HibernateInterceptor.class)
public class UserLogic {
    private final UserRepository repository;
    private final HttpSession session;

    public UserLogic(UserRepository repository, HttpSession session) {
        this.repository = repository;
        this.session = session;
    }

    public void form() {

    }

    public void save(User user) {
        this.repository.add(user);
        this.session.setAttribute(User.class.getName(), user);
    }
}
