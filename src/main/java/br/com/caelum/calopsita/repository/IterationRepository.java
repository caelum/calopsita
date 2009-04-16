package br.com.caelum.calopsita.repository;

import br.com.caelum.calopsita.model.Iteration;

public interface IterationRepository extends BaseRepository<Iteration> {
	Iteration find(String goal);

	Iteration load(Iteration iteration);
}
