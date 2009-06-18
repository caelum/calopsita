package br.com.caelum.calopsita.persistence.dao;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;

public class UserDaoTest extends AbstractDaoTest {
	private UserDao dao;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dao = new UserDao(session);
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
}
