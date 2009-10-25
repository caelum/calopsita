package br.com.caelum.calopsita.persistence.dao;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.CardType;
import br.com.caelum.calopsita.model.Project;

public class CardTypeDaoTest extends AbstractDaoTest {

	private CardTypeDao dao;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dao = new CardTypeDao(session);
	}

	@Test
	public void listingCardTypesOfAProject() throws Exception {
		Project project = givenAProject();
		Project otherProject = givenAProject();

		CardType fromProject = givenACardTypeOf(project);
		CardType fromOtherProject = givenACardTypeOf(otherProject);

		List<CardType> types = dao.listFrom(project);

		assertThat(types, hasItem(fromProject));
		assertThat(types, not(hasItem(fromOtherProject)));
	}

	private CardType givenACardTypeOf(Project project) {
		CardType type = new CardType(dao);
		type.setProject(project);
		session.save(type);
		return type;
	}

	private Project givenAProject() {
		Project project = new Project();
		project.setName("Some Project");
		session.save(project);
		return project;
	}
}
