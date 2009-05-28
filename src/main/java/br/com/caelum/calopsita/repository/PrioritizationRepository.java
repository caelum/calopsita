package br.com.caelum.calopsita.repository;

import br.com.caelum.calopsita.model.PrioritizableCard;
/**
 * Repository for PrioritizableCards
 * @author lucascs
 *
 */
public interface PrioritizationRepository {

	PrioritizableCard load(PrioritizableCard card);

}
