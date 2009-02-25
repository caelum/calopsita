package br.com.caelum.calopsita.infra.pico;

import org.vraptor.scope.ApplicationContext;

public class Paths {

	public static String fullPathFromWebApplicationRoot(final ApplicationContext appContext, final String configFileRelative) {
		String rootPath = appContext.getRealPath("/");
		return ConfigFile.ensureDoesNotEndWithSlash(rootPath) + ConfigFile.ensureStartWithSlash(configFileRelative);
	}

}
