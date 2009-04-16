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
		Story story = givenAStory();
		Story storyOfIteration = givenAStoryOfTheIteration(iteration);
		
		List<Story> list = dao.storiesWithoutIteration();
		
		assertThat(list, hasItem(story));
		assertThat(list, not(hasItem(storyOfIteration)));
	}


	private Story givenAStoryOfTheIteration(Iteration iteration) {
		Story story = givenAStory();
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


	private Iteration givenAnIteration() {
		Iteration iteration = new Iteration();
		session.save(iteration);
		session.flush();
		return iteration;
	}
	
}
