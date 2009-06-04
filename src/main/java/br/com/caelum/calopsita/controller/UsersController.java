package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.logic;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import br.com.caelum.calopsita.infra.vraptor.SessionUser;
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
public class UsersController {
    private final UserRepository repository;
    private final Validator validator;
    private final Result result;
	private final SessionUser sessionUser;

    public UsersController(Result result, Validator validator, UserRepository repository, SessionUser sessionUser) {
        this.result = result;
        this.validator = validator;
        this.repository = repository;
		this.sessionUser = sessionUser;
    }

    @Path("/users/new/") @Get @Post
    public User formSignUp() {
        return new User();
    }

    @Path("/users/") @Post
    public void save(final User user) {
    	validator.onError().goTo(UsersController.class).formSignUp();
        validator.checking(new Validations() {
            {
                that("", "user.already.exists", repository.find(user.getLogin()), is(nullValue()));
                and(Hibernate.validate(user));
            }
        });
        this.repository.add(user);
        sessionUser.setUser(user);
        result.use(logic()).redirectTo(ProjectsController.class).list();
    }

    @Path("/users/login/") @Post
    public void login(final User user) {
    	validator.onError().goTo(HomeController.class).login();
        validator.checking(new Validations() {
            {
                User found = repository.find(user.getLogin());
                that("", "login.invalid", found, is(notNullValue()));
                if (found != null) {
					that("", "login.invalid", found.getPassword(), is(equalTo(user.getPassword())));
				}
            }
        });
        sessionUser.setUser(user);
        result.use(logic()).redirectTo(ProjectsController.class).list();
    }

    @Path("/users/logout/") @Get
    public void logout() {
        sessionUser.setUser(null);
        result.use(logic()).redirectTo(HomeController.class).login();
    }
}
