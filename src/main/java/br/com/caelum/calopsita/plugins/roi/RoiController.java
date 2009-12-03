package br.com.caelum.calopsita.plugins.roi;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class RoiController {

	private final RoiRepository repository;
	private final Result result;

	public RoiController(RoiRepository repository, Result result) {
		this.repository = repository;
		this.result = result;
	}
	
    public void form() {
    	return ;
    }
}
