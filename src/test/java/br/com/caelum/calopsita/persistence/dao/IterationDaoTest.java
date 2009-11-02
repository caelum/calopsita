package br.com.caelum.calopsita.persistence.dao;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.hibernate.Session;
import org.jmock.Expectations;
import org.jmock.Sequence;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.plugins.PluginResultTransformer;
import br.com.caelum.calopsita.plugins.Transformer;

public class IterationDaoTest extends AbstractDaoTest {

    private IterationDao dao;
	private Session mockSession;

    @Override
	@Before
    public void setUp() throws Exception {
    	super.setUp();
        dao = new IterationDao(session, new PluginResultTransformer(session, Collections.<Transformer>emptyList()));
    }

    @Test
	public void orderingCardsByPriority() throws Exception {
    	Iteration iteration = givenAnIteration();
    	Card card3 = givenACard(iteration);
    	Card card1 = givenACard(iteration);

    	assertThat(dao.listCards(iteration), hasItems(card1, card3));
	}

    @Test
	public void testDelegation() throws Exception {
		IterationDao mockedDao = givenAMockedDao();
		shouldExecuteCrudInSequence();
		mockedDao.add(new Iteration());
		mockedDao.load(new Iteration());
		mockedDao.update(new Iteration());
		mockedDao.remove(new Iteration());
		mockery.assertIsSatisfied();
	}

    private IterationDao givenAMockedDao() {
		mockSession = mockery.mock(Session.class);
		return new IterationDao(mockSession, null);
	}

	private void shouldExecuteCrudInSequence() {
		final Sequence crud = mockery.sequence("crud");
		mockery.checking(new Expectations() {
			{
				one(mockSession).save(with(any(Iteration.class))); inSequence(crud);
				one(mockSession).load(Iteration.class, null); will(returnValue(new Iteration())); inSequence(crud);
				one(mockSession).update(with(any(Iteration.class))); inSequence(crud);
				one(mockSession).delete(with(any(Iteration.class))); inSequence(crud);
			}
		});
	}



	private Card givenACard(Iteration iteration) {
		Card card = new Card();
		card.setName("Abc");
		card.setDescription("Def");
		card.setIteration(iteration);
		session.save(card);
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
