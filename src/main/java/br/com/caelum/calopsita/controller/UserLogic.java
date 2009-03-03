package br.com.caelum.calopsita.controller;

import javax.servlet.http.HttpSession;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.i18n.Message;
import org.vraptor.validator.ValidationErrors;

import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.UserRepository;

@Component
@InterceptedBy( { HibernateInterceptor.class })
public class UserLogic {
    private final UserRepository repository;
    private final HttpSession session;

    public UserLogic(UserRepository repository, HttpSession session) {
        this.repository = repository;
        this.session = session;
    }

    public void formSignUp() {

    }

    public void formLogin() {

    }

    public void save(User user) {
        this.repository.add(user);
        this.session.setAttribute(User.class.getName(), user);
        this.session.setAttribute("currentUser", user);
    }

    public void validateSave(ValidationErrors errors, User user) {
        User found = repository.find(user.getLogin());
        if (found != null) {
            errors.add(new Message("user.save", "user.already.exists"));
        }
    }

    public void login(User user) {
        this.session.setAttribute(User.class.getName(), user);
        this.session.setAttribute("currentUser", user);
    }

    public void validateLogin(ValidationErrors errors, User user) {
        User found = repository.find(user.getLogin());
        if (found == null || !found.getPassword().equals(user.getPassword())) {
            errors.add(new Message("user.login", "login.invalid"));
        }
    }

    public void logout() {
        this.session.removeAttribute(User.class.getName());
        this.session.removeAttribute("currentUser");
    }
}
