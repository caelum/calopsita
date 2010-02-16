package br.com.caelum.calopsita.infra.vraptor;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import br.com.caelum.vraptor.ComponentRegistry;
import br.com.caelum.vraptor.ioc.Stereotype;

public class CalopsitaPluginParser implements JarParser {

	private final ComponentRegistry registry;

	public CalopsitaPluginParser(ComponentRegistry registry) {
		this.registry = registry;
	}

	public void parse(File file) {
		JarFile jar = newJarFile(file);
		Attributes attributes = getManifest(jar).getAttributes("br.com.caelum.calopsita");
		if(attributes==null) {
			return;
		}
		Enumeration<JarEntry> entries = jar.entries();
		while (entries.hasMoreElements()) {
			JarEntry jarEntry = entries.nextElement();
			if (jarEntry.getName().endsWith(".class")) {
				String className = jarEntry.getName().replaceFirst("\\.class$", "").replace('/', '.');
				try {
					Class<?> clazz = Class.forName(className);
					if (isAnnotatedWithVRaptorStereotype(clazz))
						registry.register(clazz, clazz);													
				} catch (ClassNotFoundException e) {
					throw new IllegalArgumentException(e);
				}
				
			}
		}
	}

	private boolean isAnnotatedWithVRaptorStereotype(Class<?> clazz) {
		for (Annotation annotation : clazz.getAnnotations()) {
			if(annotation.annotationType().isAnnotationPresent(Stereotype.class))
				return true;
		}
		return false;
	}

	private Manifest getManifest(JarFile jar){
		try {
			Manifest manifest = jar.getManifest();
			return manifest == null? new Manifest() : manifest;
		} catch (IOException e) {
			return new Manifest();
		}
	}

	private JarFile newJarFile(File file) {
		try {
			return new JarFile(file);
		} catch (IOException e) {
			throw new IllegalArgumentException("It is not a valid jar file", e);
		}
	}

}
