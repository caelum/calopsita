package br.com.caelum.calopsita.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;

@Resource
public class HomeController {
    @Get @Path("/home/login/")
    public void login() {
    }

    @Get @Path("/home/notAllowed/")
    public void notAllowed() {
    }
}
