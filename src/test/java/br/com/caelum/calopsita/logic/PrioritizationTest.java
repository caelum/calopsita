package br.com.caelum.calopsita.logic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.plugins.prioritization.PrioritizableCard;
import br.com.caelum.calopsita.plugins.prioritization.PrioritizationController;
import br.com.caelum.calopsita.plugins.prioritization.PrioritizationRepository;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.vraptor.util.test.MockResult;

public class PrioritizationTest {
    private Mockery mockery;
    private PrioritizationController logic;
	private PrioritizationRepository repository;
	private ProjectRepository projectRepository;
	private Project project;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery();
        repository = mockery.mock(PrioritizationRepository.class);

		projectRepository = mockery.mock(ProjectRepository.class);
		project = new Project(projectRepository);
		logic = new PrioritizationController(new MockResult(), repository);

		mockery.checking(new Expectations() {
			{
				allowing(projectRepository).load(project);
				will(returnValue(project));

				ignoring(repository).listCards(project);
			}
		});
    }

    @Test
	public void prioritizingCards() throws Exception {
		PrioritizableCard card = givenACard(withPriority(5));

		PrioritizableCard loaded = shouldLoadFromRepository(card);

		logic.prioritize(project, Arrays.asList(card));


		assertThat(loaded.getPriority(), is(5));
		mockery.assertIsSatisfied();

	}
	private PrioritizableCard shouldLoadFromRepository(final PrioritizableCard card) {
		final PrioritizableCard loaded = new PrioritizableCard();
		mockery.checking(new Expectations() {
			{
				one(repository).load(card);
				will(returnValue(loaded));
			}
		});
		return loaded;
	}

	private PrioritizableCard givenACard(int priority) {
		PrioritizableCard card = new PrioritizableCard();
		card.setPriority(priority);
		return card;
	}

	private int withPriority(int i) {
		return i;
	}
}
