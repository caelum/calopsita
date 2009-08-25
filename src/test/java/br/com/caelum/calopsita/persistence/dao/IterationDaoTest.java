package br.com.caelum.calopsita.persistence.dao;

import static br.com.caelum.calopsita.CustomMatchers.hasItemsInThisOrder;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.plugins.prioritization.PrioritizableCard;

public class IterationDaoTest extends AbstractDaoTest {

    private IterationDao dao;

    @Override
	@Before
    public void setUp() throws Exception {
    	super.setUp();
        dao = new IterationDao(session);
    }

    @Test
	public void orderingCardsByPriority() throws Exception {
    	Iteration iteration = givenAnIteration();
    	Card card3 = givenACard(iteration, withPriority(3));
    	Card card1 = givenACard(iteration, withPriority(1));

    	assertThat(dao.listCardsOrderedByPriority(iteration), hasItemsInThisOrder(card1, card3));
	}


    private int withPriority(int i) {
		return i;
	}

	private Card givenACard(Iteration iteration, int priority) {
		Card card = new Card();
		card.setName("Abc");
		card.setDescription("Def");
		card.setIteration(iteration);
		session.save(card);
		PrioritizableCard pCard = new PrioritizableCard(card);
		pCard.setPriority(priority);
		session.save(pCard);
		return card;
	}

	private Project givenAProject() {
        Project project = new Project();
        project.setName("A project");
        session.save(project);
        session.flush();
        return project;
    }

    private Iteration givenAnIteration() {
        Iteration iteration = new Iteration();
        iteration.setGoal("An iteration");
        iteration.setProject(givenAProject());
        session.save(iteration);
        session.flush();
        return iteration;
    }
}
