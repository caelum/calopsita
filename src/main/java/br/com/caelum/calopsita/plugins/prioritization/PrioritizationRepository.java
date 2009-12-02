package br.com.caelum.calopsita.plugins.prioritization;

import java.util.List;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Project;
/**
 * Repository for PrioritizableCards
 * @author lucascs
 *
 */
public interface PrioritizationRepository {

	PrioritizableCard load(PrioritizableCard card);

	List<List<Card>> listCards(Project project);

}
