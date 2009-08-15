package br.com.caelum.calopsita.model;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {

	private final String label;

	private final List<SubmenuItem> submenu = new ArrayList<SubmenuItem>();

	MenuItem(String label) {
		this.label = label;
	}

	public MenuItem add(SubmenuItem item) {
		this.submenu.add(item);
		return this;
	}

	public String getLabel() {
		return label;
	}
}
