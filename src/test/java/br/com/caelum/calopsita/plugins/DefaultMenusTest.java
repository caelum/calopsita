package br.com.caelum.calopsita.plugins;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Menu;
import br.com.caelum.calopsita.model.Parameters;
import br.com.caelum.calopsita.model.Project;

public class DefaultMenusTest {


	private Mockery mockery;
	private Parameters parameters;
	private DefaultMenus menus;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();

		parameters = mockery.mock(Parameters.class);

		menus = new DefaultMenus();
	}

	@Test
	public void whenThereIsNoProjectsDontCreateMenus() throws Exception {
		givenThereIsNoProject();

		Menu menu = givenAMenu();

		menus.includeMenus(menu, parameters);

		assertThat(menu.toString(), not(containsString("/projects")));
		mockery.assertIsSatisfied();
	}
	@Test
	public void whenThereIsAProjectsCreateProjectMenus() throws Exception {
		givenThereIsAProjectWithId(5l);
		givenAnythingElseIsIgnored();

		Menu menu = givenAMenu();

		menus.includeMenus(menu, parameters);

		assertThat(menu.toString(), containsString("/projects/5"));
		mockery.assertIsSatisfied();
	}
	@Test
	public void whenThereIsAnIterationCreateIterationMenus() throws Exception {
		givenThereIsAProjectWithId(5l);
		givenThereIsAnIterationWithId(9l);
		givenAnythingElseIsIgnored();

		Menu menu = givenAMenu();

		menus.includeMenus(menu, parameters);

		assertThat(menu.toString(), containsString("/projects/5/iterations/9"));
		mockery.assertIsSatisfied();
	}
	@Test
	public void whenThereIsACardCreateCardMenus() throws Exception {
		givenThereIsAProjectWithId(5l);
		givenThereIsACardWithId(12l);
		givenAnythingElseIsIgnored();

		Menu menu = givenAMenu();

		menus.includeMenus(menu, parameters);

		assertThat(menu.toString(), containsString("/projects/5/cards/12"));
		mockery.assertIsSatisfied();
	}

	private void givenThereIsACardWithId(final Long id) {
		mockery.checking(new Expectations() {
			{
				Card card = new Card();
				card.setId(id);

				one(parameters).contains("card");
				will(returnValue(true));

				one(parameters).get("card");
				will(returnValue(card));
			}
		});
	}

	private void givenThereIsAnIterationWithId(final Long id) {
		mockery.checking(new Expectations() {
			{
				Iteration iteration = new Iteration();
				iteration.setId(id);

				one(parameters).contains("iteration");
				will(returnValue(true));

				one(parameters).get("iteration");
				will(returnValue(iteration));

			}
		});
	}

	private Menu givenAMenu() {
		return new Menu("path");
	}

	private void givenAnythingElseIsIgnored() {
		mockery.checking(new Expectations() {
			{
				ignoring(anything());
			}
		});
	}

	private void givenThereIsAProjectWithId(final Long id) {
		mockery.checking(new Expectations() {
			{
				Project project = new Project();
				project.setId(id);

				one(parameters).contains("project");
				will(returnValue(true));

				one(parameters).get("project");
				will(returnValue(project));

			}
		});
	}

	private void givenThereIsNoProject() {

		mockery.checking(new Expectations() {
			{
				one(parameters).contains("project");
				will(returnValue(false));
			}
		});
	}
}
