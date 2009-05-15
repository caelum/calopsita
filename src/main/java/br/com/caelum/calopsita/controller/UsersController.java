package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.logic;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import javax.servlet.http.HttpSession;

import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.UserRepository;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Hibernate;
import br.com.caelum.vraptor.validator.Validations;

@Resource
@InterceptedBy( { HibernateInterceptor.class })
public class UsersController {
    private final UserRepository repository;
    private final HttpSession session;
    private final Validator validator;
    private final Result result;

    public UsersController(Result result, Validator validator, UserRepository repository, HttpSession session) {
        this.result = result;
        this.validator = validator;
        this.repository = repository;
        this.session = session;
    }

    @Path("/users/new") @Get
    public User form() {
        return new User();
    }

    @Path("/users") @Post
    public void save(final User user) {
        validator.checking(new Validations() {
            {
                that(repository.find(user.getLogin())).shouldBe(null);
                and(Hibernate.validate(user));
            }
        });
        this.repository.add(user);
        this.session.setAttribute(User.class.getName(), user);
        this.session.setAttribute("currentUser", user);
        result.use(logic()).redirectServerTo(ProjectsController.class).list();
    }

    @Path("/users/login") @Get
    public void login(final User user) {
        validator.checking(new Validations() {
            {
                User found = repository.find(user.getLogin());
                that(found).shouldBe(notNullValue());
                that(found.getPassword()).shouldBe(equalTo(user.getPassword()));
            }
        });
        this.session.setAttribute(User.class.getName(), user);
        this.session.setAttribute("currentUser", user);
        result.use(logic()).redirectServerTo(ProjectsController.class).list();
    }

    @Path("/users/logout") @Get
    public void logout() {
        this.session.removeAttribute(User.class.getName());
        this.session.removeAttribute("currentUser");
        result.use(logic()).redirectServerTo(HomeController.class).login();
    }
}
