package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.logic;

import java.util.ArrayList;
import java.util.List;

import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Story;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.calopsita.repository.StoryRepository;
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
@InterceptedBy( { HibernateInterceptor.class, AuthenticationInterceptor.class, AuthorizationInterceptor.class })
public class StoriesController {

	private final StoryRepository repository;
	private final User currentUser;
	private final ProjectRepository projectRepository;
	private List<Story> stories;
    private final Validator validator;
    private final Result result;

	public StoriesController(Result result, Validator validator, User user, StoryRepository repository, ProjectRepository projectRepository) {
		this.result = result;
        this.validator = validator;
        this.currentUser = user;
		this.repository = repository;
		this.projectRepository = projectRepository;
	}

	@Path("/projects/{project.id}/stories/") @Post
	public void save(final Story story, Project project) {
		story.setProject(project);
		validator.checking(new Validations() {
            {
                that(Hibernate.validate(story));
            }
        });
		repository.add(story);
		result.include("project", project);
		result.include("stories", this.projectRepository.listStoriesFrom(project));
	}
	
	@Path("/projects/{project.id}/stories/saveSub/") @Post
	public void saveSub(Story story) {
		repository.add(story);
		result.include("stories", this.repository.listSubstories(story.getParent()));
		result.include("story", story.getParent());
		result.include("project", story.getProject());
	}
	
	@Path("/projects/{project.id}/stories/{story.id}/edit/") @Post
	public void edit(Story story) {
	    result.include("story", this.repository.load(story));
	    result.include("stories", this.repository.listSubstories(story));
	}

	@Path("/projects/{project.id}/stories/{story.id}/") @Post
	public void update(Story story) {
		Story loaded = repository.load(story);
		Project project = loaded.getProject();
		loaded.setName(story.getName());
		loaded.setDescription(story.getDescription());
		repository.update(loaded);
		result.include("project", project);
		result.include("stories", this.projectRepository.listStoriesFrom(project));
		result.use(logic()).redirectTo(ProjectsController.class).show(project);
	}
	
	@Path("/projects/{project.id}/stories/prioritize/") @Post
	public void prioritize(Project project, List<Story> stories) {
		for (Story story : stories) {
			Story loaded = repository.load(story);
			loaded.setPriority(story.getPriority());
		}
		result.use(logic()).redirectTo(StoriesController.class).prioritization(project);
	}
	
	//TODO: Deveria ser método de algum modelo, n?
	public List<List<Story>> getGroupedStories() {
		List<List<Story>> result = new ArrayList<List<Story>>();
		if (stories != null) {
			for (int i = maxPriority(stories); i >= 0; i--) {
				result.add(new ArrayList<Story>());
			}
			for (Story story : stories) {
				result.get(story.getPriority()).add(story);
			}
		}
		return result;
	}

	//TODO: Deveria ser método de algum modelo, n?
	private int maxPriority(List<Story> stories2) {
		int max = 0;
		for (Story story : stories2) {
			if (story.getPriority() > max) {
				max = story.getPriority();
			}
		}
		return max;
	}

	@Path("/projects/{project.id}/stories/{story.id}/") @Delete
	public void delete(Story story, boolean deleteSubstories) {
		Story loaded = repository.load(story);
		Project project = loaded.getProject();
		if (project.getColaborators().contains(currentUser) || project.getOwner().equals(currentUser)) {
		    project = loaded.getProject();
	        if (deleteSubstories) {
	            for (Story sub : loaded.getSubstories()) {
	                repository.remove(sub);
	            }
	        } else {
	            for (Story sub : loaded.getSubstories()) {
	                sub.setParent(null);
	                repository.update(sub);
	            }
	        }
	        repository.remove(loaded);
	        result.use(logic()).redirectTo(ProjectsController.class).show(project);
		} 
	}
	
	@Path("/projects/{project.id}/priorization/") @Get
    public void prioritization(Project project) {
        result.include("project", this.projectRepository.get(project.getId()));
        result.include("stories", this.projectRepository.listStoriesFrom(project));
    }
}
