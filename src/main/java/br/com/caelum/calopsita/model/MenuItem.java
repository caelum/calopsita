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

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<ul class=\"sub_menu\">");
		for (SubmenuItem item : submenu) {
			stringBuilder.append(String.format("<li><a href=\"<c:url value=\"%s\"/>\">%s</a></li>", item.getUrl(), item.getLabel()));
		}
		stringBuilder.append("</ul>");
		return stringBuilder.toString();
	}
}
