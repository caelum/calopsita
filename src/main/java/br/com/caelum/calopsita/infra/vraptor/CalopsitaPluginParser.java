package br.com.caelum.calopsita.infra.vraptor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javax.persistence.Entity;
import javax.servlet.ServletContext;

import br.com.caelum.vraptor.ComponentRegistry;
import br.com.caelum.vraptor.ioc.Stereotype;

public class CalopsitaPluginParser implements JarParser {

	private final ComponentRegistry registry;
	private final ServletContext context;

	public CalopsitaPluginParser(ComponentRegistry registry, ServletContext context) {
		this.registry = registry;
		this.context = context;
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
				registerClass(jarEntry);
			} else if (jarEntry.getName().endsWith("messages.properties")) {
				appendMessages(getInputStream(jar, jarEntry));
			}
		}
	}

	private InputStream getInputStream(JarFile jar, JarEntry jarEntry) {
		try {
			return jar.getInputStream(jarEntry);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private void appendMessages(InputStream input) {
		String messages = context.getRealPath("/messages.properties");
		try {
			FileOutputStream writer = new FileOutputStream(new File(messages), true);
			move(input, writer);
			writer.close();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private void move(InputStream input, FileOutputStream writer) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(input);
		byte[] content = new byte[1024*10];
		int len;
		while((len = bis.read(content))!=-1) {
			writer.write(content, 0, len);
		}
	}

	private void registerClass(JarEntry jarEntry) {
		String className = jarEntry.getName().replaceFirst("\\.class$", "").replace('/', '.');
		try {
			Class<?> clazz = Class.forName(className);
			if (isAnnotatedWithVRaptorStereotype(clazz)) {
				registry.register(clazz, clazz);
			}
			if (clazz.isAnnotationPresent(Entity.class)) {
				AnnotationConfigurationFactory.addEntity(clazz);
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private boolean isAnnotatedWithVRaptorStereotype(Class<?> clazz) {
		for (Annotation annotation : clazz.getAnnotations()) {
			if(annotation.annotationType().isAnnotationPresent(Stereotype.class)) {
				return true;
			}
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
