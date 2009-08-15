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

}
