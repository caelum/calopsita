package br.com.caelum.calopsita.persistence.dao;

import static br.com.caelum.calopsita.CustomMatchers.hasSameId;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.plugins.PluginResultTransformer;
import br.com.caelum.calopsita.plugins.Transformer;

public class UserDaoTest extends AbstractDaoTest {

	private UserDao dao;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dao = new UserDao(session);
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

	private User givenAUser() {
		User user = new User(dao);
		user.setLogin("test");
		user.setPassword("test");
		user.setName("User test");
		user.setEmail("test@caelum.com.br");
		session.save(user);
		session.flush();
		return user;
	}

	private Project givenAProjectOwnedBy(User user) {
		Project project = givenAProject();
		project.setOwner(user);
		session.update(project);
		session.flush();
		return project;
	}

	private Project givenAProject() {
		Project project = new Project(new ProjectDao(session, new PluginResultTransformer(session, new ArrayList<Transformer>())));
		project.setName("Tuba");
		session.save(project);
		session.flush();
		return project;
	}



}
