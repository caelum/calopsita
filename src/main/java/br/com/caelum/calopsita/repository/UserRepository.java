package br.com.caelum.calopsita.repository;

import br.com.caelum.calopsita.model.User;

public interface UserRepository extends BaseRepository<User> {

    User find(String login);

}
