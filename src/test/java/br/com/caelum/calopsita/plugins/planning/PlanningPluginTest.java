package br.com.caelum.calopsita.plugins.planning;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Menu;
import br.com.caelum.calopsita.model.Parameters;
import br.com.caelum.calopsita.model.Project;

public class PlanningPluginTest {

	private Mockery mockery;
	private Parameters parameters;
	private PlanningPlugin menus;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();

		parameters = mockery.mock(Parameters.class);

		menus = new PlanningPlugin();
	}

	@Test
	public void shouldNotIncludePluginMenusWhenThereIsNoProject() throws Exception {
		givenThereIsNoProject();

		Menu menu = givenAMenu();
		menus.includeMenus(menu, parameters);

		assertThat(menu.toString(), not(containsString("/projects")));
		mockery.assertIsSatisfied();
	}

	@Test
	public void whenThereIsAnIterationCreatePluginMenus() throws Exception {
		givenThereIsAProjectWithId(5l);
		givenThereIsAnIterationWithId(9l);
		givenAnythingElseIsIgnored();

		Menu menu = givenAMenu();

		menus.includeMenus(menu, parameters);

		assertThat(menu.toString(), containsString("/projects/5/iterations/9"));
		mockery.assertIsSatisfied();
	}

	private Menu givenAMenu() {
		return new Menu("path");
	}
	private void givenThereIsNoProject() {

		mockery.checking(new Expectations() {
			{
				one(parameters).contains("project");
				will(returnValue(false));
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
	private void givenAnythingElseIsIgnored() {
		mockery.checking(new Expectations() {
			{
				ignoring(anything());
			}
		});
	}
}
