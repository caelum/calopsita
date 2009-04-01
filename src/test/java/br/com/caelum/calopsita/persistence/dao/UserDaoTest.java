package br.com.caelum.calopsita.persistence.dao;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;

public class UserDaoTest {
	private Session session;
	private UserDao dao;
	private Transaction transaction;

	@Before
	public void setUp() throws Exception {
		session = new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		
		dao = new UserDao(session);
		transaction = session.beginTransaction();
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

	private Project givenAProject() {
		Project project = new Project();
		project.setName("test");
		project.setDescription("test");
		this.session.save(project);
		this.session.flush();
		return project;
	}

	@After
	public void tearDown() throws Exception {
		if (transaction != null) {
			transaction.rollback();
		}
	}
}
