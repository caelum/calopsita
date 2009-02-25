package br.com.caelum.calopsita.infra.pico;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.picocontainer.MutablePicoContainer;

public class Dependencies {
	private static final Logger LOGGER = Logger.getLogger(Dependencies.class);
	private Set<Entry<String, String>> dependencies;

	public Dependencies(final ConfigFile configFile) {
		this(configFile.entrySet());
	}
	
	public Dependencies(final Map<String, String> dependencies) {
		this(dependencies.entrySet());
	}
	
	private Dependencies(Set<Entry<String, String>> dependencies) {
		this.dependencies = dependencies;
	}
	
	public void configureContainer(MutablePicoContainer spyPicoContainer) {
		for (Entry<String, Class<?>> dependency : getDependencyMap().entrySet()) {
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("Setting up dependency with key [" + dependency.getKey() + "] and implementation ["
						+ dependency.getValue() + "]");
			}
			
			spyPicoContainer.addComponent(dependency.getKey(), dependency.getValue());
		}
	}

	private Map<String, Class<?>> getDependencyMap() {
		final Map<String, Class<?>> config = new HashMap<String, Class<?>>();
		for (final Entry<String, String> prop : dependencies) {
			final String key = prop.getKey();
			final String className = prop.getValue();
			final Class<?> cls = resolveClass(key, className);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Reading dependency for key [" + key + "] with class [" + cls.getName() + "]");
			}
			config.put(key, cls);
		}
		return config;
	}

	public Class<?> resolveClass(final String key, final String className) {
		try {
			return Class.forName(className);
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException("Could not find class " + className + " when configuring dependency for key "
					+ key);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dependencies == null) ? 0 : dependencies.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dependencies other = (Dependencies) obj;
		if (dependencies == null) {
			if (other.dependencies != null)
				return false;
		}
		else if (!dependencies.equals(other.dependencies))
			return false;
		return true;
	}
}
