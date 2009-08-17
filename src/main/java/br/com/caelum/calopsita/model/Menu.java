package br.com.caelum.calopsita.model;

import java.util.ArrayList;
import java.util.List;

public class Menu {

	private final List<MenuItem> items = new ArrayList<MenuItem>();

	public MenuItem getOrCreate(String menu) {
		for (MenuItem item : items) {
			if (item.getLabel().equals(menu)) {
				return item;
			}
		}
		return new MenuItem(menu);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<div id=\"menu\">	");
		stringBuilder.append("	<ul id=\"upper_menu\">");
		for (MenuItem item : items) {
			stringBuilder.append("<li>");
			stringBuilder.append(item.getLabel());
			stringBuilder.append("</li>");
			stringBuilder.append(item.toString());
		}
		stringBuilder.append("	</ul>");
		stringBuilder.append("</div>");
		return stringBuilder.toString();
	}

}
