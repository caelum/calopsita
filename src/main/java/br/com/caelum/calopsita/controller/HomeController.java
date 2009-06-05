package br.com.caelum.calopsita.controller;

import br.com.caelum.calopsita.model.User;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;

@Resource
public class HomeController {
    @Get @Post
    public User login() {
        return new User();
    }

    @Get
    public void notAllowed() {
    }
}
