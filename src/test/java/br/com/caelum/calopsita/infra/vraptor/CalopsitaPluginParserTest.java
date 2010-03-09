package br.com.caelum.calopsita.infra.vraptor;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.ComponentRegistry;
public class CalopsitaPluginParserTest {

	private CalopsitaPluginParser parser;
	private @Mock ComponentRegistry registry;
	private @Mock ServletContext context;

	@Before
	public void configure() {
		MockitoAnnotations.initMocks(this);
		parser = new CalopsitaPluginParser(registry, context);
	}

	@Test
	public void whenThereIsNoCalopsitaSectionDoNothing() throws IOException {
		parser.parse(jarWithManifest(""));
	}

	@Test
	public void whenThereIsNoManifestDoNothing() throws IOException {
		parser.parse(jarWithManifest(null));
	}

	@Test
	public void registersClassIfItIsAVRaptorType() throws IOException {
		parser.parse(jarWithClass(AVRaptorResource.class));

		verify(registry).register(AVRaptorResource.class, AVRaptorResource.class);
	}

	@Test
	public void shouldAddEntitiesToAnnotationConfiguration() throws IOException {
		parser.parse(jarWithClass(AnEntity.class));

		assertThat(AnnotationConfigurationFactory.getEntities().toString(), AnnotationConfigurationFactory.getEntities().size(), is(1));
		assertEquals(AnnotationConfigurationFactory.getEntities().get(0), AnEntity.class);
	}

	@Test
	public void doesNotRegisterClassIfItIsNotAVRaptorType() throws IOException {
		parser.parse(jarWithClass(NotAVRaptorResource.class));

		verify(registry, never()).register(NotAVRaptorResource.class, NotAVRaptorResource.class);
	}

	@Test
	public void findsPluginMessagePropertiesAndAppendToCalopsitas() throws Exception {
		File messages = File.createTempFile("messages", ".properties");
		messages.deleteOnExit();
		PrintWriter writer = new PrintWriter(messages);
		writer.println("some.existing = content");
		writer.close();

		when(context.getRealPath("/messages.properties")).thenReturn(messages.getAbsolutePath());

		parser.parse(jarWithFile("messages.properties", "another = content"));

		Properties properties = new Properties();
		properties.load(new FileReader(messages));

		assertThat(properties.getProperty("some.existing"), is("content"));
		assertThat(properties.getProperty("another"), is("content"));
	}

	private File jarWithManifest(String manifest) throws IOException {
		File tmp = File.createTempFile("test-calopsita-plugin", ".jar");
		tmp.deleteOnExit();
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(tmp));
		createEntry(out, "foo.txt", "bar");
		if (manifest != null) {
			createEntry(out, "META-INF/MANIFEST.MF", manifest);
		}
		out.close();
		return tmp;
	}



	private File jarWithClass(Class<?> clazz) throws IOException {
		String typePath = clazz.getName().replace('.', '/') + ".class";
		InputStream resourceAsStream = CalopsitaPluginParserTest.class.getResourceAsStream("/" + typePath);
		File tmp = File.createTempFile("test-calopsita-plugin", ".jar");
		tmp.deleteOnExit();
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(tmp));
		createEntry(out, typePath, resourceAsStream);
		addManifest(out);
		out.close();
		return tmp;
	}

	private File jarWithFile(String filename, String content) throws IOException {
		File tmp = File.createTempFile("test-calopsita-plugin", ".jar");
		tmp.deleteOnExit();
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(tmp));
		createEntry(out, filename, content);
		addManifest(out);
		out.close();
		return tmp;
	}
	private void addManifest(ZipOutputStream out) throws IOException {
		String manifest = "Manifest-Version: 1.0\nClassPath: \n\nName: br.com.caelum.calopsita\na: b\n\n";
		createEntry(out, "META-INF/MANIFEST.MF", manifest);
	}

	private void createEntry(ZipOutputStream out, String filename, InputStream is) throws IOException {
		out.putNextEntry(new ZipEntry(filename));
		byte[] buf = new byte[1024];
		int len;
		while ((len = is.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		out.closeEntry();
	}

	private void createEntry(ZipOutputStream out, String filename, String content) throws IOException {
		out.putNextEntry(new ZipEntry(filename));
		PrintWriter writer = new PrintWriter(out, true);
		writer.print(content);
		writer.flush();
		out.closeEntry();
	}

}
