package br.com.caelum.calopsita.repository;

import java.util.List;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.CardType;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;

public interface ProjectRepository extends BaseRepository<Project> {

    List<Project> listAllFrom(User user);

	Project get(Long id);

	List<Card> listCardsFrom(Project project);

	Project load(Project project);

	List<Iteration> listIterationsFrom(Project project);

	Iteration getCurrentIterationFromProject(Project project);

	boolean hasInconsistentValues(Object[] parameters, User user);

	List<CardType> listCardTypesFrom(Project project);

	Project refresh(Project project);

	List<User> listUnrelatedUsers(Project project);

	List<Card> planningCardsWithoutIteration(Project project);

}
