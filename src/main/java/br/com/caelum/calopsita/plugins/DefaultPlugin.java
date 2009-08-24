package br.com.caelum.calopsita.plugins;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Menu;
import br.com.caelum.calopsita.model.Parameters;
import br.com.caelum.calopsita.model.PluginConfig;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.SubmenuItem;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class DefaultPlugin implements PluginConfig {

	public String getName() {
		return "Default";
	}

	public String getDescription() {
		return "The Default plugin. Should be included in all projects";
	}

	public void includeMenus(Menu menu, Parameters parameters) {
		Project project = parameters.get("project");
		if (project == null) {
			return;
		}
		menu.getOrCreate("iteration.current")
			.add(new SubmenuItem("iteration.current", "/projects/" + project.getId() + "/iterations/current/"));

		menu.getOrCreate("iterations")
			.add(new SubmenuItem("iterations.all", "/projects/" + project.getId() + "/iterations/"))
			.add(new SubmenuItem("project.addIteration", "/projects/" + project.getId() + "/iterations/new/"));

		if (parameters.contains("iteration")) {
			Iteration iteration = parameters.get("iteration");
			menu.getOrCreate("iteration.current")
				.add(new SubmenuItem("iteration.edit", "/projects/" + project.getId() + "/iterations/" + iteration.getId() + "/edit/"));
			menu.getOrCreate("iterations")
				.add(new SubmenuItem("iteration.edit", "/projects/" + project.getId() + "/iterations/" + iteration.getId() + "/edit/"));
		}

		menu.getOrCreate("cards")
			.add(new SubmenuItem("cards.pending", "/projects/" + project.getId() + "/cards/"))
			.add(new SubmenuItem("cards.all", "/projects/" + project.getId() + "/cards/all/"))
			.add(new SubmenuItem("project.addCard", "/projects/" + project.getId() + "/cards/new/"));

		if (parameters.contains("card")) {
			Card card = parameters.get("card");
			menu.getOrCreate("cards")
				.add(new SubmenuItem("card.edit", "/projects/" + project.getId() + "/cards/" + card.getId() + "/edit/"))
				.add(new SubmenuItem("card.subcard.new", "/projects/" + project.getId() + "/cards/" + card.getId() + "/subcards/new/"))
				.add(new SubmenuItem("card.subcards", "/projects/" + project.getId() + "/cards/" + card.getId() + "/subcards/"));
		}

		menu.getOrCreate("admin")
			.add(new SubmenuItem("project.edit", "/projects/" + project.getId() + "/edit/"))
			.add(new SubmenuItem("colaborators", "/projects/" + project.getId() + "/colaborators/"))
			.add(new SubmenuItem("cardTypes", "/projects/" + project.getId() + "/cardTypes/"));

	}

}
