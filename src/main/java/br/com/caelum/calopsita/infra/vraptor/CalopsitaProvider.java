package br.com.caelum.calopsita.infra.vraptor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import br.com.caelum.iogi.Instantiator;
import br.com.caelum.vraptor.ComponentRegistry;
import br.com.caelum.vraptor.http.ParametersProvider;
import br.com.caelum.vraptor.http.iogi.IogiParametersProvider;
import br.com.caelum.vraptor.http.iogi.VRaptorInstantiator;
import br.com.caelum.vraptor.ioc.spring.SpringProvider;
import br.com.caelum.vraptor.util.hibernate.SessionFactoryCreator;

public class CalopsitaProvider extends SpringProvider {

	private ComponentRegistry registry;
	
	@Override
	protected void registerCustomComponents(ComponentRegistry registry) {
		this.registry = registry;
		this.registry.register(SessionFactoryCreator.class, SessionFactoryCreator.class);
		this.registry.register(ParametersProvider.class, IogiParametersProvider.class);
		this.registry.register(Instantiator.class, VRaptorInstantiator.class);
	}

	private void move(InputStream input, FileOutputStream writer) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(input);
		byte[] content = new byte[1024*10];
		int len;
		while((len = bis.read(content))!=-1) {
			writer.write(content, 0, len);
		}
	}

	@Override
	public void start(ServletContext context) {
		
		String messages = context.getRealPath("/messages.properties");
		String calopsitas = context.getRealPath("/calopsita.properties");
		try {
			FileOutputStream writer = new FileOutputStream(new File(messages), false);
			move(new FileInputStream(calopsitas), writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		File libDirectory = new File(context.getRealPath("/WEB-INF/lib"));
		File[] files = libDirectory.listFiles(new JarFilter());
		CalopsitaPluginParser jarParser = new CalopsitaPluginParser(registry, context);
		for (File file : files) {
			jarParser.parse(file);
		}
		super.start(context);
	}
	
	
	static class JarFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return name.endsWith(".jar");
		}
	}

}
