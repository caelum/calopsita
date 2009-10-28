package br.com.caelum.calopsita.plugins.planning;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Menu;
import br.com.caelum.calopsita.model.Parameters;
import br.com.caelum.calopsita.model.PluginConfig;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.SubmenuItem;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class PlanningPlugin implements PluginConfig {

	public String getDescription() {
		return null;
	}

	public String getName() {
		return "Planning";
	}

	public void includeMenus(Menu menu, Parameters parameters) {
		if (parameters.contains("project") && parameters.contains("iteration")) {
			Project project = parameters.get("project");
			Iteration iteration = parameters.get("iteration");
			menu.getOrCreate("iterations")
				.add(new SubmenuItem("iteration.plan", "/projects/" + project.getId() + "/iterations/" + iteration.getId() + "/"));

			menu.getOrCreate("iteration.current")
				.add(new SubmenuItem("iteration.plan", "/projects/" + project.getId() + "/iterations/" + iteration.getId() + "/"));
		}
	}

}
