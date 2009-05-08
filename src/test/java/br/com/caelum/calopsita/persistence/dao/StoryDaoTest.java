package br.com.caelum.calopsita.persistence.dao;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

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

public class StoryDaoTest {

	
	private Session session;
	private StoryDao dao;
	private Transaction transaction;

	@Before
	public void setUp() throws Exception {
		session = new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		
		dao = new StoryDao(session);
		transaction = session.beginTransaction();
	}
	
	
	@After
	public void tearDown() throws Exception {
		if (transaction != null) {
			transaction.rollback();
		}
	}
	
	@Test
	public void storiesWithoutIteration() throws Exception {
		Iteration iteration = givenAnIteration();
		Story story = givenAStory(iteration.getProject());
		Story storyOfIteration = givenAStoryOfTheIteration(iteration);
		Story storyOfOtherProject = givenAStory(givenAProject());
		
		List<Story> list = dao.storiesWithoutIteration(iteration.getProject());
		
		assertThat(list, hasItem(story));
		assertThat(list, not(hasItem(storyOfIteration)));
		assertThat(list, not(hasItem(storyOfOtherProject)));
	}
	@Test
	public void listingSubstories() throws Exception {
		Story story = givenAStory();
		Story substory = givenASubStory(story);
		Story otherStory = givenAStory();
		List<Story> list = dao.listSubstories(story);
		
		assertThat(list, hasItem(substory));
		assertThat(list, not(hasItem(story)));
		assertThat(list, not(hasItem(otherStory)));
	}


	private Story givenASubStory(Story story) {
		Story sub = givenAStory();
		sub.setParent(story);
		session.flush();
		return sub;
	}


	private Story givenAStoryOfTheIteration(Iteration iteration) {
		Story story = givenAStory(iteration.getProject());
		story.setIteration(iteration);
		session.update(story);
		session.flush();
		return story;
	}


	
	private Story givenAStory() {
		Story story = new Story();
		story.setName("Rumpelstitlskin");
		story.setDescription("I hope I spelld his name correctly");
		session.save(story);
		session.flush();
		return story;
		
	}
	private Story givenAStory(Project project) {
		Story story = givenAStory();
		story.setProject(project);
		session.flush();
		return story;
	}


	private Iteration givenAnIteration() {
		Iteration iteration = new Iteration();
		iteration.setProject(givenAProject());
		session.save(iteration);
		session.flush();
		return iteration;
	}
	
	private Project givenAProject() {
		Project project = new Project();
		project.setName("A project");
		session.save(project);
		session.flush();
		return project;
	}
}
