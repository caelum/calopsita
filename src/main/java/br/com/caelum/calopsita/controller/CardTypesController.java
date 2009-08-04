package br.com.caelum.calopsita.controller;

import static br.com.caelum.vraptor.view.Results.logic;
import br.com.caelum.calopsita.model.CardType;
import br.com.caelum.calopsita.model.Gadgets;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
@Resource
public class CardTypesController {

	private final Result result;

	public CardTypesController(Result result) {
		this.result = result;
	}

	@Path("/projects/{project.id}/cardTypes/") @Get
	public void list(Project project) {
		result.include("project", project);
		result.include("cardTypeList", project.getCardTypes());
		result.include("gadgets", Gadgets.values());
	}

	@Path("/projects/{cardType.project.id}/cardTypes/") @Post
	public void save(CardType cardType) {
		cardType.save();
		result.use(logic()).redirectTo(CardTypesController.class).list(cardType.getProject());
	}
}
