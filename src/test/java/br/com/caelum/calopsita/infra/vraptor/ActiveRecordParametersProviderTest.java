package br.com.caelum.calopsita.infra.vraptor;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.ResourceBundle;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.picocontainer.annotations.Inject;

import br.com.caelum.vraptor.http.ognl.OgnlParametersProvider;
import br.com.caelum.vraptor.ioc.Container;
import br.com.caelum.vraptor.resource.ResourceMethod;

public class ActiveRecordParametersProviderTest {


	private ActiveRecordParametersProvider provider;
	private OgnlParametersProvider delegate;
	private Container container;
	private Mockery mockery;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		mockery.setImposteriser(ClassImposteriser.INSTANCE);
		container = mockery.mock(Container.class);
		delegate = mockery.mock(OgnlParametersProvider.class);
		provider = new ActiveRecordParametersProvider(delegate, container);
	}
	@Test
	public void shouldSetDependency() throws Exception {
		givenDelegateReturnsAMyClassInstance();
		givenContainerWillProvideArgument();

		provider.getParametersFor(null, null, null);

		assertTrue(MyClass.wasSet);
	}

	private void givenContainerWillProvideArgument() {

		mockery.checking(new Expectations() {
			{
				one(container).instanceFor(String.class);
				will(returnValue("Anything"));
			}
		});
	}
	private void givenDelegateReturnsAMyClassInstance() {

		mockery.checking(new Expectations() {
			{
				one(delegate).getParametersFor(with(any(ResourceMethod.class)), with(any(List.class)), with(any(ResourceBundle.class)));
				will(returnValue(new Object[] {new MyClass()}));
			}
		});
	}

	static class MyClass {
		static boolean wasSet = false;
		@Inject
		public void setAnything(String type) {
			wasSet = true;
		}
	}
}
