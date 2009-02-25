package br.com.caelum.calopsita.controller;

import org.vraptor.annotations.Component;

import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.UserRepository;

@Component
public class UserLogic {
    private final UserRepository repository;

    public UserLogic(UserRepository repository) {
        this.repository = repository;
    }

    public void form() {

    }

    public void save(User user) {
        if (user.getId() != null) {
            this.repository.update(user);
        } else {
            this.repository.add(user);
        }
    }
}
