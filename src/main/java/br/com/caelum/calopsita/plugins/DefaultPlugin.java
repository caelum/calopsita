package br.com.caelum.calopsita.plugins;

import br.com.caelum.calopsita.model.Menu;
import br.com.caelum.calopsita.model.Parameters;
import br.com.caelum.calopsita.model.PluginConfig;
import br.com.caelum.calopsita.model.SubmenuItem;

public class DefaultPlugin implements PluginConfig {

	public String getName() {
		return "Default";
	}

	public String getDescription() {
		return "The Default plugin. Should be included in all projects";
	}

	public void includeMenus(Menu menu, Parameters parameters) {
		menu.getOrCreate("iteration.current")
			.add(new SubmenuItem("iteration.current", "/projects/${project.id}/iterations/current/"));
		if (parameters.contains("iteration")) {
			menu.getOrCreate("iteration.current")
				.add(new SubmenuItem("edit", "/projects/${project.id}/iterations/${iteration.id}/edit/"));
		}
		menu.getOrCreate("iterations")
			.add(new SubmenuItem("iterations.all", "/projects/${project.id}/iterations/"))
			.add(new SubmenuItem("project.addIteration", "/projects/${project.id}/iterations/new/"));

		if (parameters.contains("iteration")) {
			menu.getOrCreate("iterations")
				.add(new SubmenuItem("edit", "/projects/${project.id}/iterations/${iteration.id}/edit/"));
		}

		menu.getOrCreate("cards")
			.add(new SubmenuItem("cards.all", "/projects/${project.id}/cards/"))
			.add(new SubmenuItem("project.addCard", "/projects/${project.id}/cards/new/"));

		if (parameters.contains("card")) {
			menu.getOrCreate("cards")
				.add(new SubmenuItem("edit", "/projects/${project.id}/cards/${cards.id}/edit/"))
				.add(new SubmenuItem("card.subcard.new", "/projects/${project.id}/cards/${cards.id}/subcards/new/"))
				.add(new SubmenuItem("card.subcards", "/projects/${project.id}/cards/${cards.id}/subcards/"));
		}

		menu.getOrCreate("admin")
			.add(new SubmenuItem("edit", "/projects/${project.id}/edit/"))
			.add(new SubmenuItem("colaborators", "/projects/${project.id}/colaborators/"))
			.add(new SubmenuItem("cardTypes", "/projects/${project.id}/cardTypes/"));

	}

}
