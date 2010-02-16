package br.com.caelum.calopsita.infra.vraptor;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

import javax.servlet.ServletContext;

import br.com.caelum.iogi.Instantiator;
import br.com.caelum.vraptor.ComponentRegistry;
import br.com.caelum.vraptor.http.ParametersProvider;
import br.com.caelum.vraptor.http.iogi.IogiParametersProvider;
import br.com.caelum.vraptor.http.iogi.VRaptorInstantiator;
import br.com.caelum.vraptor.ioc.spring.SpringProvider;
import br.com.caelum.vraptor.util.hibernate.SessionFactoryCreator;

public class CalopsitaProvider extends SpringProvider {

	private JarParser jarParser;

	
	
	@Override
	protected void registerCustomComponents(ComponentRegistry registry) {
		registry.register(SessionFactoryCreator.class, SessionFactoryCreator.class);
		registry.register(ParametersProvider.class, IogiParametersProvider.class);
		registry.register(Instantiator.class, VRaptorInstantiator.class);
		this.jarParser = new CalopsitaPluginParser(registry);
	}

	@Override
	public void start(ServletContext context) {
		
		File libDirectory = new File(context.getRealPath("/WEB-INF/lib"));
		File[] files = libDirectory.listFiles(new JarFilter());
		for (File file : files) {
			this.jarParser.parse(file);
		}
		super.start(context);
	}
	
	
	static class JarFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return name.endsWith(".jar");
		}
	}

}
