package br.com.caelum.calopsita.repository;

import java.util.List;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;

public interface UserRepository extends BaseRepository<User> {

    User find(String login);

	List<User> listAll();

	List<User> listUnrelatedUsers(Project project);

}
