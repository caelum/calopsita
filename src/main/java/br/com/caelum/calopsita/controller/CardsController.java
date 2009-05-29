package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.logic;
import static br.com.caelum.vraptor.view.Results.page;
import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.CardRepository;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;

@Resource
public class CardsController {

	private final CardRepository repository;
	private final User currentUser;
	private final ProjectRepository projectRepository;
    private final Validator validator;
    private final Result result;

	public CardsController(Result result, Validator validator, SessionUser user, CardRepository repository, ProjectRepository projectRepository) {
		this.result = result;
        this.validator = validator;
        this.currentUser = user.getUser();
		this.repository = repository;
		this.projectRepository = projectRepository;
	}

	@Path("/projects/{project.id}/cards/") @Post
	public void save(final Card card, Project project) {
		card.setProject(project);
		validator.checking(new Validations() {
            {
                that(Hibernate.validate(card));
            }
        });
		repository.add(card);
		result.include("project", project);
		result.include("cards", this.projectRepository.listCardsFrom(project));
		result.use(page()).forward("/WEB-INF/jsp/cards/update.jsp");
	}

	@Path("/projects/{project.id}/cards/saveSub/") @Post
	public void saveSub(Card card) {
		repository.add(card);
		result.include("stories", this.repository.listSubcards(card.getParent()));
		result.include("story", card.getParent());
		result.include("project", card.getProject());
		result.use(page()).forward("/WEB-INF/jsp/cards/update.jsp");
	}

	@Path("/cards/{card.id}/") @Get
	public void edit(Card card) {
	    result.include("card", this.repository.load(card));
	    result.include("cards", this.repository.listSubcards(card));
	}

	@Path("/cards/{card.id}/") @Post
	public void update(Card card) {
		Card loaded = repository.load(card);
		Project project = loaded.getProject();
		loaded.setName(card.getName());
		loaded.setDescription(card.getDescription());
		repository.update(loaded);
		result.include("project", project);
		result.include("stories", this.projectRepository.listStoriesFrom(project));
		result.use(logic()).redirectTo(ProjectsController.class).show(project);
	}
	
	@Path("/projects/{project.id}/stories/prioritize/") @Post
	public void prioritize(Project project, List<Card> cards) {
		for (Card card : cards) {
			Card loaded = repository.load(card);
			loaded.setPriority(card.getPriority());
		}
		result.use(logic()).redirectTo(CardsController.class).prioritization(project);
	}
	
	//TODO: Deveria ser método de algum modelo, n?
	public List<List<Card>> getGroupedCards() {
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

	@Path("/stories/{story.id}/") @Delete
	public void delete(Card card, boolean deleteSubstories) {
		Card loaded = repository.load(card);
		Project project = loaded.getProject();
		if (project.getColaborators().contains(currentUser) || project.getOwner().equals(currentUser)) {
		    project = loaded.getProject();
	        if (deleteSubstories) {
	            for (Card sub : loaded.getSubcards()) {
	                repository.remove(sub);
	            }
	        } else {
	            for (Card sub : loaded.getSubcards()) {
	                sub.setParent(null);
	                repository.update(sub);
	            }
	        }
	        repository.remove(loaded);
	        result.use(logic()).redirectTo(ProjectsController.class).cards(project);
		}
	}
	
	@Path("/projects/{project.id}/priorization/") @Get
    public void prioritization(Project project) {
        result.include("project", this.projectRepository.get(project.getId()));
        result.include("stories", this.projectRepository.listStoriesFrom(project));
    }
}
