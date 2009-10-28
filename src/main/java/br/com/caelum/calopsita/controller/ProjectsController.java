package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.logic;
import static br.com.caelum.vraptor.view.Results.page;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Hibernate;
import br.com.caelum.vraptor.validator.Validations;

@Resource
public class ProjectsController {

    private final User currentUser;
    private final Validator validator;
    private final Result result;

    public ProjectsController(Validator validator, Result result, SessionUser user) {
        this.result = result;
        this.validator = validator;
        this.currentUser = user.getUser();
    }

    @Path("/projects/new/") @Get
    public void form() {
    }

    @Path("/projects/{project.id}/edit/") @Get
    public Project edit(Project project) {
    	return project.load();
    }

    @Path("/projects/") @Post
    public void save(final Project project) {
        project.setOwner(currentUser);
        validator.checking(new Validations() {{
        	and(Hibernate.validate(project));
        }});
        validator.onErrorUse(page()).of(ProjectsController.class).form();
        project.save();
        result.use(logic()).redirectTo(ProjectsController.class).list();
    }

    @Path("/projects/{project.id}/") @Post
    public void update(Project project) {
    	final Project loaded = project.load();
    	validator.checking(new Validations() {{
    		that(loaded.getOwner(), equalTo(currentUser));
    	}});
    	validator.onErrorUse(page()).forward("/WEB-INF/jsp/projects/delete.invalid.jsp");
		loaded.setDescription(project.getDescription());
		result.use(logic()).redirectTo(ProjectsController.class).edit(loaded);
    }

    @Path("/projects/{project.id}/") @Delete
    public void delete(Project project) {
    	final Project loaded = project.load();
    	validator.checking(new Validations() {{
			that(currentUser, is(loaded.getOwner()));
    	}});
    	validator.onErrorUse(page()).forward("/WEB-INF/jsp/projects/delete.invalid.jsp");
	    loaded.delete();
	    result.use(logic()).redirectTo(ProjectsController.class).list();
    }

    @Path({"/projects/", "/"}) @Get
    public List<Project> list() {
        return currentUser.getProjects();
    }

    @Path("/projects/{project.id}/colaborators/") @Get
    public Project listColaborators(Project project) {
    	return project.load();
    }

    @Path("/projects/{project.id}/colaborators/") @Post
    public void addColaborator(Project project, User colaborator) {
        Project loaded = project.load();
        loaded.getColaborators().add(colaborator);
        result.use(logic()).redirectTo(ProjectsController.class).listColaborators(project);
    }
}
