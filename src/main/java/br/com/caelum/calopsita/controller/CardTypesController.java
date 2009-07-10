package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.logic;
import br.com.caelum.calopsita.model.CardType;
import br.com.caelum.calopsita.model.Gadgets;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.CardTypeRepository;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
@Resource
public class CardTypesController {

	private final Result result;
	private final CardTypeRepository repository;

	public CardTypesController(Result result, CardTypeRepository repository) {
		this.result = result;
		this.repository = repository;
	}

	@Path("/projects/{project.id}/cardTypes/") @Get
	public void list(Project project) {
		result.include("project", project);
		result.include("cardTypeList", this.repository.listFrom(project));
		result.include("gadgets", Gadgets.values());
	}

	@Path("/projects/{cardType.project.id}/cardTypes/") @Post
	public void save(CardType cardType) {
		this.repository.save(cardType);
		result.use(logic()).redirectTo(ProjectsController.class).admin(cardType.getProject());
	}
}
