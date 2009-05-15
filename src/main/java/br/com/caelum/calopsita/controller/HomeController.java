package br.com.caelum.calopsita.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
@Resource
public class HomeController {
    @Get
    public User login() {
        return new User();
    }

    @Path("/users/notAllowed/") @Get
    public void notAllowed() {
    }
}
