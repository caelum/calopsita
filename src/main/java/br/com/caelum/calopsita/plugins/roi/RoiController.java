package br.com.caelum.calopsita.plugins.roi;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import static br.com.caelum.vraptor.view.Results.*;

@Resource
public class RoiController {

	private final RoiRepository repository;
	private final Result result;

	public RoiController(RoiRepository repository, Result result) {
		this.repository = repository;
		this.result = result;
	}
	
	@Path("/roi/{card.id}/") @Post
    public void save(ValuableCard card) {
		repository.update(card);
		result.use(nothing());
    }
}
