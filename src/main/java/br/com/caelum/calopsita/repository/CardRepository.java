package br.com.caelum.calopsita.repository;

import java.util.List;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Card;

public interface CardRepository extends BaseRepository<Card>{

	Card load(Card story);

	void update(Card story);

	List<Card> cardsWithoutIteration(Project project);

	List<Card> listSubcards(Card story);

}
