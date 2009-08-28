package br.com.caelum.calopsita.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuItem {

	private final String label;

	private final List<SubmenuItem> submenu = new ArrayList<SubmenuItem>();

	private final String contextPath;

	private final ResourceBundle bundle;

	MenuItem(String label, String contextPath) {
		this.label = label;
		this.contextPath = contextPath;
		bundle = ResourceBundle.getBundle("messages");
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
		stringBuilder.append("<ul class=\"submenu\" id=\"menu_").append(label.replace('.', '_')).append("\">");
		for (SubmenuItem item : submenu) {
			stringBuilder.append(String.format("<li><a href=\"%s%s\">%s</a></li>", contextPath, item.getUrl(),
					bundle.getString(item.getLabel())));
		}
		stringBuilder.append("</ul>");
		return stringBuilder.toString();
	}
}
