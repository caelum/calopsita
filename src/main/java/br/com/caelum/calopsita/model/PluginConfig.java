package br.com.caelum.calopsita.model;

import br.com.caelum.vraptor.core.MethodInfo;

public interface PluginConfig {

	String getName();

	void activate(Menu menu, MethodInfo parameters);

}
