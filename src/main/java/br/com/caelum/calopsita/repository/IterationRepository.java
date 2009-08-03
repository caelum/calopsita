package br.com.caelum.calopsita.repository;

import java.util.List;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Iteration;

public interface IterationRepository extends BaseRepository<Iteration> {
	Iteration load(Iteration iteration);

	List<Card> listCardsOrderedByPriority(Iteration iteration);
}
