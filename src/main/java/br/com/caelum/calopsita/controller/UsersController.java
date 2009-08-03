package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.logic;
import static br.com.caelum.vraptor.view.Results.nothing;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.model.User;
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
    private final Validator validator;
    private final Result result;
	private final SessionUser sessionUser;

    public UsersController(Result result, Validator validator, SessionUser sessionUser) {
        this.result = result;
        this.validator = validator;
		this.sessionUser = sessionUser;
    }

    @Path("/users/new/") @Get
    public User formSignUp() {
        return new User();
    }

    @Path("/users/") @Post
    public void save(final User user) {
    	validator.onError().goTo(UsersController.class).formSignUp();
        validator.checking(new Validations() {
            {
                that("", "user.already.exists", user.load(), is(nullValue()));
                and(Hibernate.validate(user));
            }
        });
        user.setNewbie(true);
        user.save();
        sessionUser.setUser(user);
        result.use(logic()).redirectTo(ProjectsController.class).list();
    }

    @Path("/users/login/") @Post
    public void login(final User user) {
    	validator.onError().goTo(HomeController.class).login();
        validator.checking(new Validations() {
            {
                User found = user.load();
                that("", "login.invalid", found, is(notNullValue()));
                if (found != null) {
					that("", "login.invalid", found.getPassword(), is(equalTo(user.getPassword())));
				}
            }
        });
        sessionUser.setUser(user.load());
        result.use(logic()).redirectTo(ProjectsController.class).list();
    }

    @Path("/users/logout/") @Get
    public void logout() {
        sessionUser.setUser(null);
        result.use(logic()).redirectTo(HomeController.class).login();
    }

    @Path("/users/toggleNewbie/") @Get
    public void toggleNewbie() {
    	User user = sessionUser.getUser().load();
    	user.toggleNewbie();
    	sessionUser.setUser(user);
    	result.use(nothing());
    }
}
