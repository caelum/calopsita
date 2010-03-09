package br.com.caelum.calopsita.infra.vraptor;

import static org.mockito.Mockito.verify;
import net.vidageek.mirror.dsl.Mirror;

import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AnnotationConfigurationTest {

	private AnnotationConfigurationFactory factory;

	private @Mock AnnotationConfiguration configuration;
	private class MockedClass {};

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		factory = new AnnotationConfigurationFactory();

		new Mirror().on(factory).set().field("configuration").withValue(configuration);
	}

	@Test
	public void shouldAddGivenClassesToAnnotationConfiguration() throws Exception {
		AnnotationConfigurationFactory.addEntity(MockedClass.class);
		factory.addEntitiesToConfiguration();
		verify(configuration).addAnnotatedClass(MockedClass.class);
		AnnotationConfigurationFactory.getEntities().clear();

	}
}
