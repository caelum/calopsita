package br.com.caelum.calopsita.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Plugin {

	@Id
	private Class<? extends PluginConfig> pluginClass;

	public void setPluginClass(Class<? extends PluginConfig> pluginClass) {
		this.pluginClass = pluginClass;
	}

	public Class<? extends PluginConfig> getPluginClass() {
		return pluginClass;
	}


}
