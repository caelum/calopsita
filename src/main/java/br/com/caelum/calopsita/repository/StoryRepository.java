package br.com.caelum.calopsita.repository;

import java.util.List;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Card;

public interface StoryRepository extends BaseRepository<Card>{

	Card load(Card story);

	void update(Card story);

	List<Card> storiesWithoutIteration(Project project);

	List<Card> listSubstories(Card story);

}
