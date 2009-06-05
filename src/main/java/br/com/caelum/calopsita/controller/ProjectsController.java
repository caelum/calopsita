package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.logic;
import static br.com.caelum.vraptor.view.Results.page;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.model.Project;
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
public class ProjectsController {

    private final ProjectRepository repository;
    private final User currentUser;
	private final UserRepository userRepository;
    private final Validator validator;
    private final Result result;

    public ProjectsController(Validator validator, Result result, ProjectRepository repository, UserRepository userRepository, SessionUser user) {
        this.result = result;
        this.validator = validator;
        this.repository = repository;
		this.userRepository = userRepository;
        this.currentUser = (user == null? null:user.getUser());
    }

    @Path("/projects/new/") @Get
    public Project form() {
        return new Project();
    }

    @Path("/projects/{project.id}/admin/") @Get
    public void admin(Project project) {
    	this.result.include("project", this.repository.get(project.getId()));
    	this.result.include("users", this.userRepository.listUnrelatedUsers(project));
    }

    @Path("/projects/{project.id}/cards/") @Get
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

    @Path("/projects/{project.id}/") @Post
    public void update(Project project) {
    	validator.onError().goTo(page()).forward("/WEB-INF/jsp/projects/delete.invalid.jsp");
    	final Project loaded = this.repository.load(project);
    	validator.checking(new Validations() {
    		{
    			that(loaded.getOwner(), equalTo(currentUser));
    		}
    	});
		loaded.setDescription(project.getDescription());
		result.use(logic()).redirectTo(ProjectsController.class).admin(loaded);
    }

    @Path("/projects/{project.id}/") @Delete
    public void delete(Project project) {
    	final Project loaded = this.repository.load(project);
    	validator.checking(new Validations() {
    		{
    			that(currentUser).shouldBe(equalTo(loaded.getOwner()));
    		}
    	});
	    this.repository.remove(loaded);
	    result.use(logic()).redirectTo(ProjectsController.class).list();
    }

    @Path("/projects/") @Get
    public List<Project> list() {
        return repository.listAllFrom(currentUser);
    }

    @Path("/") @Get
    public void index() {
    	result.use(logic()).redirectTo(ProjectsController.class).list();
    }

    @Path("/projects/{project.id}/colaborators/") @Post
    public void addColaborator(Project project, User colaborator) {
        Project loaded = repository.get(project.getId());
        loaded.getColaborators().add(colaborator);
        repository.update(loaded);
        result.include("project", loaded);
        result.use(logic()).redirectTo(ProjectsController.class).admin(project);
    }
}
