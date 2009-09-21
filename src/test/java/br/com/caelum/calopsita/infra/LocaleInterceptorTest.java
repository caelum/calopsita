package br.com.caelum.calopsita.infra;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.infra.interceptor.LocaleInterceptor;
import br.com.caelum.calopsita.infra.interceptor.LocaleInterceptor.DateFormat;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.util.test.MockResult;

public class LocaleInterceptorTest {


	private HttpServletRequest request;
	private Mockery mockery;
	private LocaleInterceptor interceptor;
	private MockResult result;
	private InterceptorStack stack;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		request = mockery.mock(HttpServletRequest.class);
		result = new MockResult();
		stack = mockery.mock(InterceptorStack.class);

		mockery.checking(new Expectations() {
			{
				ignoring(stack);
			}
		});
		interceptor = new LocaleInterceptor(result, request);
	}

	@Test
	public void returnGivenLocaleIfItIsSupported() throws Exception {
		givenLocaleIsPortuguese();
		interceptor.intercept(stack, null, null);

		assertEquals(result.included("format"), DateFormat.pt);
	}
	@Test
	public void returnEnglishFormatIfLocaleIsNotSupported() throws Exception {
		givenLocaleIsFrench();

		interceptor.intercept(stack, null, null);

		assertEquals(result.included("format"), DateFormat.en);
	}

	private void givenLocaleIsFrench() {
		mockery.checking(new Expectations() {
			{
				atLeast(1).of(request).getLocale();
				will(returnValue(Locale.FRENCH));
			}
		});
	}

	private void givenLocaleIsPortuguese() {
		mockery.checking(new Expectations() {
			{
				atLeast(1).of(request).getLocale();
				will(returnValue(new Locale("pt", "br")));
			}
		});
	}
}
