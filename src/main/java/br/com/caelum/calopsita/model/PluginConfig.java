package br.com.caelum.calopsita.model;

public interface PluginConfig {

	String getName();

	String getDescription();

	void includeMenus(Menu menu, Parameters parameters);

}
