package br.com.caelum.calopsita.plugins.roi;

import br.com.caelum.calopsita.repository.BaseRepository;

/**
 * Repository for ValuableCards
 * 
 * @author ceci
 */
public interface RoiRepository extends BaseRepository<ValuableCard>{

	ValuableCard load(ValuableCard card);

}