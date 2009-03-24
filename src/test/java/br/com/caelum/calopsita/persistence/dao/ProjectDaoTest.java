package br.com.caelum.calopsita.persistence.dao;

import static br.com.caelum.calopsita.CustomMatchers.hasSameId;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Story;
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
        Story storyFromOtherProject = givenAStory();
        Story storyFromThisProject = givenAStoryOfProject(project);
        
        List<Story> list = dao.listStoriesFrom(project);
        
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
	
	private Iteration givenAnIterationOfProject(Project project) throws ParseException {
        Iteration iteration = givenAnIteration();
        iteration.setProject(project);
        session.update(iteration);
        session.flush();
        return iteration;
    }


    private Iteration givenAnIteration() throws ParseException {
	    Iteration iteration = new Iteration();
	    iteration.setTarget("Be ready");
	    iteration.setStartDate("01/01/2000");
	    iteration.setEndDate("10/01/2000");
	    session.save(iteration);
	    session.flush();
	    return iteration;
    }


    private Story givenAStoryOfProject(Project project) {
		Story story = givenAStory();
		story.setProject(project);
		session.update(story);
		session.flush();
		return story;
	}


	private Story givenAStory() {
		Story story = new Story();
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
