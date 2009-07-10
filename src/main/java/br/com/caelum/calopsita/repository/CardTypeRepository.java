package br.com.caelum.calopsita.repository;

import java.util.List;

import br.com.caelum.calopsita.model.CardType;
import br.com.caelum.calopsita.model.Project;

public interface CardTypeRepository {

	void save(CardType cardType);

	List<CardType> listFrom(Project project);
}
