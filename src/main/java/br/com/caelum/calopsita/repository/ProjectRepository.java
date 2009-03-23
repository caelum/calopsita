package br.com.caelum.calopsita.repository;

import java.util.List;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Story;
import br.com.caelum.calopsita.model.User;

public interface ProjectRepository extends BaseRepository<Project> {

    List<Project> listAllFrom(User user);

	Project get(Long id);

	List<Story> listStoriesFrom(Project project);

    List<Iteration> listIterationsFrom(Project project);

}
