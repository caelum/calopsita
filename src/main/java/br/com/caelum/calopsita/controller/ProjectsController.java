package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.logic;

import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.calopsita.repository.UserRepository;
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
@InterceptedBy( { AuthenticationInterceptor.class, AuthorizationInterceptor.class })
public class ProjectsController {

    private final ProjectRepository repository;
    private final User currentUser;
	private final UserRepository userRepository;
    private final Validator validator;
    private final Result result;

    public ProjectsController(Validator validator, Result result, ProjectRepository repository, UserRepository userRepository, User user) {
        this.result = result;
        this.validator = validator;
        this.repository = repository;
		this.userRepository = userRepository;
        this.currentUser = user;
    }

    @Path("/projects/new/") @Get
    public Project form() {
        return new Project();
    }

    @Path("/projects/admin/") @Get
    public void admin(Project project) {
    	this.result.include("project", this.repository.get(project.getId()));
    	this.result.include("users", this.userRepository.listUnrelatedUsers(project));
    }


    public void cards(Project project) {
    	this.result.include("project", this.repository.get(project.getId()));
    	this.result.include("cards",  this.repository.listCardsFrom(project));
    }
    
    @Path("/projects/") @Post
    public void save(final Project project) {
        project.setOwner(currentUser);
        validator.checking(new Validations() {
            {
                that(Hibernate.validate(project));
            }
        });
        this.repository.add(project);
        result.use(logic()).redirectTo(ProjectsController.class).list();
    }

    @Path("/projects/{project.id}") @Delete
    public void delete(Project project) {
    	Project loaded = this.repository.load(project);
    	if (currentUser.equals(loaded.getOwner())) {
    	    this.repository.remove(loaded);
    	    result.use(logic()).redirectServerTo(ProjectsController.class).list();
    	}
    }
    @Path("/projects/{project.id}/") @Post
    public void update(Project project) {
    	Project loaded = this.repository.load(project);
    	if (currentUser.equals(loaded.getOwner())) {
    		loaded.setDescription(project.getDescription());
    		result.use(logic()).redirectTo(IterationsController.class).current(loaded);
    	}
    }

    @Path("/projects/{project.id}/") @Delete
    public void delete(Project project) {
    	Project loaded = this.repository.load(project);
    	if (currentUser.equals(loaded.getOwner())) {
    	    this.repository.remove(loaded);
    	    result.use(logic()).redirectTo(ProjectsController.class).list();
    	}
    }

    @Path("/projects/") @Get
    public void list() {
        result.include("projects", repository.listAllFrom(currentUser));
    }

    @Path("/") @Get
    public void index() {
    	list();
    }

    @Path("/projects/{project.id}/addColaborator/") @Post
    public void addColaborator(Project project, User colaborator) {
        Project loaded = repository.get(project.getId());
        loaded.getColaborators().add(colaborator);
        repository.update(loaded);
        result.include("project", loaded);
        result.use(logic()).redirectTo(IterationsController.class).current(project);
    }
}
