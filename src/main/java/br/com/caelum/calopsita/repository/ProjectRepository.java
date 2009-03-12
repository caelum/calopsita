package br.com.caelum.calopsita.repository;

import java.util.List;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;

public interface ProjectRepository extends BaseRepository<Project> {

    List<Project> listAllFromOwner(User user);

	Project get(Long id);

}
