package br.com.caelum.calopsita.repository;

import java.util.List;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;

public interface IterationRepository extends BaseRepository<Iteration> {
	Iteration load(Iteration iteration);

    Iteration getCurrentIterationFromProject(Project project);
    
    List<Iteration> listIterationsFrom(Project project);
}
