package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.logic;
import static br.com.caelum.vraptor.view.Results.nothing;
import static br.com.caelum.vraptor.view.Results.page;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
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
    public void formSignUp() {
    }

    @Path("/users/") @Post
    public void save(final User user) {
        validator.checking(new Validations() {
            {
                that(user.load(), is(nullValue()), "", "user.already.exists");
                and(Hibernate.validate(user));
            }
        });
        validator.onErrorUse(page()).of(UsersController.class).formSignUp();
        user.setNewbie(true);
        user.save();
        sessionUser.setUser(user);
        result.use(logic()).redirectTo(ProjectsController.class).list();
    }

    @Path("/users/login/") @Post
    public void login(final User user) {
    	final User found = user.load();
        validator.checking(new Validations() {
            {
                if (that(found, is(notNullValue()), "", "login.invalid")) {
					that(found.getPassword(), is(equalTo(user.getPassword())), "", "login.invalid");
				}
            }
        });
        validator.onErrorUse(page()).of(HomeController.class).login();
        sessionUser.setUser(found);
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
