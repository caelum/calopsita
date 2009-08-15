package br.com.caelum.calopsita.model;

public class SubmenuItem {

	private final String label;

	private final String url;

	public SubmenuItem(String label, String url) {
		this.label = label;
		this.url = url;
	}

	public String getLabel() {
		return label;
	}

	public String getUrl() {
		return url;
	}

}
