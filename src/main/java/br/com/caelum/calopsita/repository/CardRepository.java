package br.com.caelum.calopsita.repository;

import java.util.List;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Gadget;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;

public interface CardRepository extends BaseRepository<Card>{

	Card load(Card card);

	void update(Card card);

	List<Card> cardsWithoutIteration(Project project);

	List<Card> listSubcards(Card card);

	<T extends Gadget> T load(T card);

	List<Card> listFrom(Project project);

	void orderCardsByPriority(Iteration iteration);

}
