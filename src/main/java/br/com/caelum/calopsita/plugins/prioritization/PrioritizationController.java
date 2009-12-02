package br.com.caelum.calopsita.plugins.prioritization;

import static br.com.caelum.vraptor.view.Results.nothing;

import java.util.List;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class PrioritizationController {

	private final PrioritizationRepository repository;
	private final Result result;

	public PrioritizationController(Result result, PrioritizationRepository repository) {
		this.result = result;
		this.repository = repository;
	}

	@Path("/projects/{project.id}/prioritization/") @Get
    public void prioritization(Project project) {
        result.include("project", project.load());
        result.include("cards", this.repository.listCards(project));
    }

	@Path("/projects/{project.id}/prioritize/") @Post
	public void prioritize(Project project, List<PrioritizableCard> cards) {
		for (PrioritizableCard card : cards) {
			PrioritizableCard loaded = repository.load(card);
			loaded.setPriority(card.getPriority());
		}
		prioritization(project);
		result.use(nothing());
	}

}
