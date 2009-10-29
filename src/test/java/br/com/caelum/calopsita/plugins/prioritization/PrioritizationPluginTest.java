package br.com.caelum.calopsita.plugins.prioritization;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Menu;
import br.com.caelum.calopsita.model.Parameters;
import br.com.caelum.calopsita.model.Project;

public class PrioritizationPluginTest {

	private Mockery mockery;
	private Parameters parameters;
	private PrioritizationPlugin menus;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();

		parameters = mockery.mock(Parameters.class);

		menus = new PrioritizationPlugin();
	}

	@Test
	public void shouldNotIncludePluginMenusWhenThereIsNoProject() throws Exception {
		givenThereIsNoProject();

		Menu menu = givenAMenu();
		menus.includeMenus(menu, parameters);

		assertThat(menu.toString(), not(containsString("/planning")));
		mockery.assertIsSatisfied();
	}

	@Test
	public void whenThereIsAnIterationCreatePluginMenus() throws Exception {
		givenThereIsAProjectWithId(5l);
		givenAnythingElseIsIgnored();

		Menu menu = givenAMenu();

		menus.includeMenus(menu, parameters);

		assertThat(menu.toString(), containsString("/projects/5/prioritization"));
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
