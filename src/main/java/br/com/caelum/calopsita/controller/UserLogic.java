package br.com.caelum.calopsita.controller;

import javax.servlet.http.HttpSession;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.i18n.Message;
import org.vraptor.validator.ValidationErrors;

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
        this.session.setAttribute("currentUser", user);
    }

    public void validateSave(ValidationErrors errors, User user) {
    	User found = repository.find(user.getLogin());
    	if (found != null) {
    		errors.add(new Message("user.login", "user.already.exists"));
    	}
    }
}
