package br.com.caelum.calopsita.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Menu {

	private final List<MenuItem> items = new ArrayList<MenuItem>();
	private final String contextPath;
	private final ResourceBundle bundle;

	public Menu(String contextPath) {
		this.contextPath = contextPath;
		bundle = ResourceBundle.getBundle("messages");
	}

	public MenuItem getOrCreate(String label) {
		for (MenuItem item : items) {
			if (item.getLabel().equals(label)) {
				return item;
			}
		}
		MenuItem menuItem = new MenuItem(label, contextPath);
		items.add(menuItem);
		return menuItem;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<ul id=\"menu\">");
		for (MenuItem item : items) {
			stringBuilder.append("<li>");
			stringBuilder.append("<a href=\"#\">");
			stringBuilder.append(bundle.getString(item.getLabel()));
			stringBuilder.append("</a>");
			stringBuilder.append("</li>");
			stringBuilder.append(item.toString());
		}
		stringBuilder.append("</ul>");
		return stringBuilder.toString();
	}

}
