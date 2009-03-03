package br.com.caelum.calopsita.repository;

import java.util.List;

import br.com.caelum.calopsita.model.Project;

public interface ProjectRepository extends BaseRepository<Project> {

	List<Project> listAll();

}
