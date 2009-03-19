package br.com.caelum.calopsita.persistence.dao;

import static br.com.caelum.calopsita.CustomMatchers.hasSameId;
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

import br.com.caelum.calopsita.model.Project;
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
