package br.com.caelum.calopsita.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
@Resource
public class HomeController {
	private final Result result;

	public HomeController(Result result) {
		this.result = result;
	}
    @Get //@Path("/home/login/")
    public void login() {
    }

    @Path("/users/notAllowed/") @Get
    public void notAllowed() {
    }
}
