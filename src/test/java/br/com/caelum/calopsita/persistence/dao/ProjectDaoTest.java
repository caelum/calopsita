package br.com.caelum.calopsita.persistence.dao;

import static br.com.caelum.calopsita.CustomMatchers.hasSameId;
import static br.com.caelum.calopsita.CustomMatchers.isEmpty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;

public class ProjectDaoTest extends AbstractDaoTest {

	private ProjectDao dao;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dao = new ProjectDao(session);
	}

	@Test
	public void listProjectIfUserIsTheOwner() throws Exception {
		User user = givenAUser();
		Project project = givenAProjectOwnedBy(user);

		List<Project> list = dao.listAllFrom(user);

		assertThat(list, hasItem(hasSameId(project)));
	}
	@Test
	public void refreshingAProject() throws Exception {
		Project project = givenAProject();

		session.evict(project);

		Project project2 = new Project(project.getId());
		project2.setRepository(dao);
		project2.refresh();

		assertThat(project2.getName(), is(project.getName()));
	}
	@Test
	public void listProjectIfUserIsAColaborator() throws Exception {
		User user = givenAUser();
		Project project = givenAProjectWithColaborator(user);

		List<Project> list = dao.listAllFrom(user);

		assertThat(list, hasItem(hasSameId(project)));
	}


	@Test
	public void dontListProjectIfUserIsUnrelated() throws Exception {
		User user = givenAUser();
		Project project = givenAProject();

		List<Project> list = dao.listAllFrom(user);

		assertThat(list, not(hasItem(hasSameId(project))));
	}

	@Test
    public void onlyListCardsFromTheGivenProject() throws Exception {
        Project project = givenAProject();
        Card cardFromOtherProject = givenACard();
        Card cardFromThisProject = givenACardOfProject(project);

        List<Card> list = dao.listCardsFrom(project);

        assertThat(list, not(hasItem(hasSameId(cardFromOtherProject))));
        assertThat(list, hasItem(hasSameId(cardFromThisProject)));
    }

	@Test
    public void onlyListIterationsFromTheGivenProject() throws Exception {
        Project project = givenAProject();
        Iteration iterationFromOtherProject = givenAnIteration();
        Iteration iterationFromThisProject = givenAnIterationOfProject(project);

        List<Iteration> list = dao.listIterationsFrom(project);

        assertThat(list, not(hasItem(hasSameId(iterationFromOtherProject))));
        assertThat(list, hasItem(hasSameId(iterationFromThisProject)));
    }

	@Test
	public void removeAProjectAlsoRemoveRelatedCardsAndIterations() throws Exception {
		Project project = givenAProject();
		givenAnIterationOfProject(project);
		givenACardOfProject(project);

		dao.remove(project);

		assertThat(dao.listIterationsFrom(project), isEmpty());
		assertThat(dao.listCardsFrom(project), isEmpty());
	}

	@Test
	public void aProjectNotOwnedByAUserIsAnInconsistentValue() throws Exception {
		Project project = givenAProject();
		User user = givenAUser();

		assertThat(dao.hasInconsistentValues(new Object[] {project}, user), is(true));
	}
	@Test
	public void aProjectNotOwnedByAUserIsAConsistentValue() throws Exception {
		User user = givenAUser();
		Project project1 = givenAProjectOwnedBy(user);
		Project project2 = givenAProjectWithColaborator(user);

		assertThat(dao.hasInconsistentValues(new Object[] {project1, project2}, user), is(false));
	}
	@Test
	public void checkingForInconsistentValuesInIterations() throws Exception {
		User user = givenAUser();

		Iteration unrelatedProject = givenAnIterationOfProject(givenAProject());
		assertThat(dao.hasInconsistentValues(new Object[] {unrelatedProject}, user), is(true));

		Iteration projectOwned = givenAnIterationOfProject(givenAProjectOwnedBy(user));
		Iteration projectWithColaborator = givenAnIterationOfProject(givenAProjectWithColaborator(user));
		assertThat(dao.hasInconsistentValues(new Object[] {projectOwned, projectWithColaborator}, user), is(false));
	}
	@Test
	public void iterationWithWrongProjectId() throws Exception {
		User user = givenAUser();
		Iteration iteration = givenAnIterationOfProject(givenAProjectOwnedBy(user));
		session.evict(iteration);
		iteration.setProject(givenAProjectOwnedBy(user));

		assertThat(dao.hasInconsistentValues(new Object[] {iteration}, user), is(true));
	}
	@Test
	public void cardWithWrongProjectId() throws Exception {
		User user = givenAUser();
		Card card = givenACardOfProject(givenAProjectOwnedBy(user));
		session.evict(card);
		card.setProject(givenAProjectOwnedBy(user));

		assertThat(dao.hasInconsistentValues(new Object[] {card}, user), is(true));
	}
	@Test
	public void cardWithoutIdWithAProjectIdIsConsistent() throws Exception {
		User user = givenAUser();
		Card card = givenACardOfProject(givenAProjectOwnedBy(user));
		session.evict(card);
		card.setId(null);

		assertThat(dao.hasInconsistentValues(new Object[] {card}, user), is(false));
	}

	@Test(expected=IllegalArgumentException.class)
	public void iterationWithoutProjectIdThrowsException() throws Exception {
		Iteration projectWithoutId = new Iteration();
		projectWithoutId.setProject(new Project());

		dao.hasInconsistentValues(new Object[] {projectWithoutId}, givenAUser());
	}

	@Test(expected=IllegalArgumentException.class)
	public void iterationWithoutProjectThrowsException() throws Exception {
		Iteration outOfProject = new Iteration();
		dao.hasInconsistentValues(new Object[] {outOfProject}, givenAUser());
	}

	@Test
	public void findUnrelatedUsers() throws Exception {
		Project project = givenAProject();
		User owner = givenAnUserOwnerOf(project);
		User colaborator = givenAnUserColaboratorOf(project);
		User user = givenAnUnrelatedUser("pedro");

		List<User> users = dao.listUnrelatedUsers(project);

		assertThat(users, hasItem(user));
		assertThat(users, not(hasItem(owner)));
		assertThat(users, not(hasItem(colaborator)));

	}

	@Test
	public void findUnreleatedUsersWhenThereIsNoColaborator() throws Exception {
		Project project = givenAProject();
		User owner = givenAnUserOwnerOf(project);
		User user = givenAnUnrelatedUser("pedro");

		List<User> users = dao.listUnrelatedUsers(project);

		assertThat(users, hasItem(user));
		assertThat(users, not(hasItem(owner)));
	}

	private User givenAnUserColaboratorOf(Project project) {
		User user = givenAnUnrelatedUser("caue");
		project.setColaborators(Arrays.asList(user));
		this.session.flush();
		return user;
	}

	private User givenAnUserOwnerOf(Project project) {
		User user = givenAnUnrelatedUser("lucas");
		project.setOwner(user);
		this.session.flush();
		return user;
	}

	private User givenAnUnrelatedUser(String name) {
		User user = new User();
		user.setName(name);
		user.setEmail(name + "@caelum.com.br");
		user.setLogin(name);
		user.setPassword(name);
		this.session.save(user);
		this.session.flush();
		return user;
	}

	private Iteration givenAnIterationOfProject(Project project) throws ParseException {
        Iteration iteration = givenAnIteration();
        iteration.setProject(project);
        session.update(iteration);
        session.flush();
        return iteration;
    }


    private Iteration givenAnIteration() throws ParseException {
	    Iteration iteration = new Iteration();
	    iteration.setGoal("Be ready");
	    iteration.setStartDate(new LocalDate(2000,1,1));
	    iteration.setEndDate(new LocalDate(2000,1,10));
	    session.save(iteration);
	    session.flush();
	    return iteration;
    }


    private Card givenACardOfProject(Project project) {
		Card card = givenACard();
		card.setProject(project);
		session.update(card);
		session.flush();
		return card;
	}


	private Card givenACard() {
		Card card = new Card();
		card.setName("Snow White");
		card.setDescription("She hangs out with the dwarves");
		session.save(card);
		session.flush();
		return card;
	}


	private Project givenAProjectWithColaborator(User user) {
		Project project = givenAProject();
		project.getColaborators().add(user);
		session.update(project);
		session.flush();
		return project;
	}
	private Project givenAProjectOwnedBy(User user) {
		Project project = givenAProject();
		project.setOwner(user);
		session.update(project);
		session.flush();
		return project;
	}

	private Project givenAProject() {
		Project project = new Project();
		project.setName("Tuba");
		session.save(project);
		session.flush();
		return project;
	}

	private User givenAUser() {
		User user = new User();
		user.setLogin("test");
		user.setPassword("test");
		user.setName("User test");
		user.setEmail("test@caelum.com.br");
		session.save(user);
		session.flush();
		return user;
	}
}
