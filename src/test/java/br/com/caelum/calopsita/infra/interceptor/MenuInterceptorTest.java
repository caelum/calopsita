package br.com.caelum.calopsita.infra.interceptor;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.DefaultParameters;
import br.com.caelum.calopsita.model.Menu;
import br.com.caelum.calopsita.model.Parameters;
import br.com.caelum.calopsita.model.PluginConfig;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.util.test.MockResult;

public class MenuInterceptorTest {


	private Mockery mockery;
	private MockResult result;
	private HttpServletRequest request;
	private PluginConfig config;
	private MenuInterceptor interceptor;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		result = new MockResult();
		request = mockery.mock(HttpServletRequest.class);
		config = mockery.mock(PluginConfig.class);
		interceptor = new MenuInterceptor(new DefaultParameters(result), result, request, Collections.singleton(config));
	}

	@Test
	public void shouldIncludeConfigOnMenu() throws Exception {
		shouldAskPluginToRegisterMenus();

		whenInterceptOccurs();

		shouldIncludeMenuOnResult();
		mockery.assertIsSatisfied();
	}

	private void whenInterceptOccurs() {
		interceptor.intercept(mockery.mock(InterceptorStack.class), null, null);
	}

	private void shouldIncludeMenuOnResult() {
		assertThat(result.included("menu"), is(instanceOf(Menu.class)));
	}

	private void shouldAskPluginToRegisterMenus() {

		mockery.checking(new Expectations() {
			{
				one(config).includeMenus(with(any(Menu.class)), with(any(Parameters.class)));
				ignoring(anything());
			}
		});
	}
}
