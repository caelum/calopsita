package br.com.caelum.calopsita.controller;

import java.util.List;

import br.com.caelum.calopsita.model.CardType;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.CardTypeRepository;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class CardTypeController {

	private final Result result;
	private final CardTypeRepository repository;

	public CardTypeController(Result result, CardTypeRepository repository) {
		this.result = result;
		this.repository = repository;
	}

	@Path("/projects/{project.id}/cardType/")
	public List<CardType> list(Project project) {
		return this.repository.listFrom(project);
	}
	@Path("/projects/{cardType.project.id}/cardType/") @Post
	public void save(CardType cardType) {

	}
}
