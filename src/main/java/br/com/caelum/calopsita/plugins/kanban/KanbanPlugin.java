package br.com.caelum.calopsita.plugins.kanban;

import br.com.caelum.calopsita.model.Menu;
import br.com.caelum.calopsita.model.Parameters;
import br.com.caelum.calopsita.model.PluginConfig;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.SubmenuItem;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class KanbanPlugin implements PluginConfig {

	public String getName() {
		return "Kanban" ;
	}

	public String getDescription() {
		return "Allows you to use kanban columns.";
	}

	public void includeMenus(Menu menu, Parameters parameters) {
		if (parameters.contains("project")) {
			Project project = parameters.get("project");
			menu.getOrCreate("admin")
				.add(new SubmenuItem("kanban.configuration", "/projects/" + project.getId()+ "/kanban_configuration"));
		}
	}

}
