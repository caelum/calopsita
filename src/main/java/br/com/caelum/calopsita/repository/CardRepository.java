package br.com.caelum.calopsita.repository;

import java.util.List;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Gadget;
import br.com.caelum.calopsita.model.Gadgets;
import br.com.caelum.calopsita.model.Project;

public interface CardRepository extends BaseRepository<Card>{

	Card load(Card card);

	void update(Card card);

	List<Card> listSubcards(Card card);

	List<Card> listRootFrom(Project project);

	void add(Gadget gadget);

	List<Gadget> listGadgets(Card card);

	void updateGadgets(Card card, List<Gadgets> gadgets);

}
