package br.com.caelum.calopsita.infra.vraptor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.ComponentRegistry;

public class CalopsitaPluginParserTest {

	private CalopsitaPluginParser parser;
	private ComponentRegistry registry;
	private Mockery mockery;

	@Before
	public void configure() {
		this.mockery = new Mockery();
		registry = mockery.mock(ComponentRegistry.class);
		parser = new CalopsitaPluginParser(registry);
	}

	@Test
	public void whenThereIsNoCalopsitaSectionDoNothing() throws IOException {
		File f = createJar("");
		parser.parse(f);
	}

	@Test
	public void whenThereIsNoManifestDoNothing() throws IOException {
		File f = createJar(null);
		parser.parse(f);
	}

	@Test
	public void registersClassIfItIsAVRaptorType() throws IOException {
		File f = createJarWithClass("Manifest-Version: 1.0\nClassPath: \n\nName: br.com.caelum.calopsita\na: b\n\n", AVRaptorResource.class);
		mockery.checking(new Expectations() {
			{
				one(registry).register(AVRaptorResource.class, AVRaptorResource.class);
			}
		});
		parser.parse(f);
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void doesNotRegisterClassIfItIsNotAVRaptorType() throws IOException {
		File f = createJarWithClass("Manifest-Version: 1.0\nClassPath: \n\nName: br.com.caelum.calopsita\na: b\n\n", NotAVRaptorResource.class);
		mockery.checking(new Expectations() {
			{
				never(registry).register(NotAVRaptorResource.class, NotAVRaptorResource.class);
			}
		});
		parser.parse(f);
		mockery.assertIsSatisfied();
	}	

	private File createJar(String manifest) throws IOException {
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

	private File createJarWithClass(String manifest, Class<?> clazz) throws IOException {
		String typePath = clazz.getName().replace('.', '/') + ".class";
		InputStream resourceAsStream = CalopsitaPluginParserTest.class.getResourceAsStream("/" + typePath);
		File tmp = File.createTempFile("test-calopsita-plugin", ".jar");
		tmp.deleteOnExit();
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(tmp));
		createEntry(out, typePath, resourceAsStream);
		if (manifest != null) {
			createEntry(out, "META-INF/MANIFEST.MF", manifest);
		}
		out.close();
		return tmp;
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
