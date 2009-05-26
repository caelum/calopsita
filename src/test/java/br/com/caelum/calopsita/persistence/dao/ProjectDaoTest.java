package br.com.caelum.calopsita.persistence.dao;

import static br.com.caelum.calopsita.CustomMatchers.hasSameId;
import static br.com.caelum.calopsita.CustomMatchers.isEmpty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.User;

public class ProjectDaoTest {

	
	private Session session;
	private ProjectDao dao;
	private Transaction transaction;

	@Before
	public void setUp() throws Exception {
		session = new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		
		dao = new ProjectDao(session);
		transaction = session.beginTransaction();
	}
	
	
	@After
	public void tearDown() throws Exception {
		if (transaction != null) {
			transaction.rollback();
		}
	}
	
	@Test
	public void listProjectIfUserIsTheOwner() throws Exception {
		User user = givenAUser();
		Project project = givenAProjectOwnedBy(user);
		
		List<Project> list = dao.listAllFrom(user);
		
		assertThat(list, hasItem(hasSameId(project)));
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
    public void onlyListStoriesFromTheGivenProject() throws Exception {
        Project project = givenAProject();
        Card storyFromOtherProject = givenAStory();
        Card storyFromThisProject = givenAStoryOfProject(project);
        
        List<Card> list = dao.listCardsFrom(project);
        
        assertThat(list, not(hasItem(hasSameId(storyFromOtherProject))));
        assertThat(list, hasItem(hasSameId(storyFromThisProject)));
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
	public void removeAProjectAlsoRemoveRelatedStoriesAndIterations() throws Exception {
		Project project = givenAProject();
		givenAnIterationOfProject(project);
		givenAStoryOfProject(project);
		
		dao.remove(project);
		
		assertThat(dao.listIterationsFrom(project), isEmpty());
		assertThat(dao.listCardsFrom(project), isEmpty());
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


    private Card givenAStoryOfProject(Project project) {
		Card story = givenAStory();
		story.setProject(project);
		session.update(story);
		session.flush();
		return story;
	}


	private Card givenAStory() {
		Card story = new Card();
		story.setName("Snow White");
		story.setDescription("She hangs out with the dwarves");
		session.save(story);
		session.flush();
		return story;
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
