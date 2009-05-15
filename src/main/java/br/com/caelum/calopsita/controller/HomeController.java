package br.com.caelum.calopsita.controller;

import br.com.caelum.calopsita.model.User;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;

@Resource
public class HomeController {
    @Get @Path("/home/login/")
    public User login() {
        return new User();
    }
    
    @Path("/users/notAllowed/") @Get
    public void notAllowed() {
    }
}
