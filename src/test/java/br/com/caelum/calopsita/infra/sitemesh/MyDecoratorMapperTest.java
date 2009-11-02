package br.com.caelum.calopsita.infra.sitemesh;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import javax.servlet.http.HttpServletRequest;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.DecoratorMapper;

public class MyDecoratorMapperTest {


	private Mockery mockery;
	private MyDecoratorMapper mapper;
	private DecoratorMapper parent;
	private HttpServletRequest request;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();

		parent = mockery.mock(DecoratorMapper.class);
		request = mockery.mock(HttpServletRequest.class);
		mapper = new MyDecoratorMapper();
		mapper.init(null, null, parent);
	}

	@Test
	public void shouldReturnNullWhenItUriIsExcluded() throws Exception {
		givenThereTheRequestMethodIs("POST");
		givenThereTheUriIs("/projects/3/cards/");

		Decorator decorator = mapper.getDecorator(request, null);
		assertThat(decorator, is(nullValue()));
		mockery.assertIsSatisfied();
	}
	@Test
	public void shouldReturnNullWhenItUriIsExcludedOnNamedDecorator() throws Exception {
		givenThereTheRequestMethodIs("POST");
		givenThereTheUriIs("/projects/3/cards/");

		Decorator decorator = mapper.getNamedDecorator(request, "whatever");
		assertThat(decorator, is(nullValue()));
		mockery.assertIsSatisfied();
	}
	@Test
	public void shouldDelegateToParentWhenUriIsNotExcluded() throws Exception {
		givenThereTheRequestMethodIs("DELETE");
		givenThereTheUriIs("/projects/3/interations/");

		shouldCallParentDecorator();

		mapper.getDecorator(request, null);
		mockery.assertIsSatisfied();
	}
	@Test
	public void shouldDelegateToParentWhenUriIsNotExcludedOnNamedDecorator() throws Exception {
		givenThereTheRequestMethodViaGetMethodIs("DELETE");
		givenThereTheUriIs("/projects/3/interations/");

		shouldCallParentNamedDecorator();

		mapper.getNamedDecorator(request, null);
		mockery.assertIsSatisfied();
	}

	private void givenThereTheRequestMethodViaGetMethodIs(final String method) {

		mockery.checking(new Expectations() {
			{
				one(request).getParameter("_method"); will(returnValue(null));
				one(request).getMethod(); will(returnValue(method));
			}
		});
	}

	private void shouldCallParentNamedDecorator() {

		mockery.checking(new Expectations() {
			{
				one(parent).getNamedDecorator(request, null);
			}
		});
	}

	private void shouldCallParentDecorator() {

		mockery.checking(new Expectations() {
			{
				one(parent).getDecorator(request, null);
			}
		});
	}

	private void givenThereTheUriIs(final String uri) {

		mockery.checking(new Expectations() {
			{
				one(request).getRequestURI(); will(returnValue(uri));
				one(request).getContextPath(); will(returnValue(""));
			}
		});
	}

	private void givenThereTheRequestMethodIs(final String method) {

		mockery.checking(new Expectations() {
			{
				one(request).getParameter("_method"); will(returnValue(method));
			}
		});
	}
}
