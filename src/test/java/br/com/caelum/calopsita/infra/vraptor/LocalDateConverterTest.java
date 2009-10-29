package br.com.caelum.calopsita.infra.vraptor;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.converter.ConversionError;
import br.com.caelum.vraptor.core.RequestInfo;
import br.com.caelum.vraptor.http.MutableRequest;

public class LocalDateConverterTest {


	private Mockery mockery;
	private MutableRequest request;
	private LocalDateConverter converter;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();

		request = mockery.mock(MutableRequest.class);
		converter = new LocalDateConverter(new RequestInfo(null, null, request, null));
	}

	@Test
	public void whenValueIsBlankOrNullShouldReturnNull() throws Exception {
		assertNull(converter.convert(null, LocalDate.class, null));
		assertNull(converter.convert("", LocalDate.class, null));
	}
	@Test
	public void whenValueIsAnInvalidDateShouldThrowConversionError() throws Exception {
		givenLocaleIs(Locale.ENGLISH);
		try {
			converter.convert("an unparseable date", LocalDate.class, null);
			fail("expected ConversionError");
		} catch (ConversionError e) {
			mockery.assertIsSatisfied();
		}
	}
	@Test
	public void whenValueIsAValidDateShouldGetCorrespondingDate() throws Exception {
		givenLocaleIs(Locale.ENGLISH);
		LocalDate date = converter.convert("10/29/2009", LocalDate.class, null);
		assertThat(date, is(new LocalDate(2009, 10, 29)));
		mockery.assertIsSatisfied();
	}

	private void givenLocaleIs(final Locale locale) {

		mockery.checking(new Expectations() {
			{
				one(request).getLocale();
				will(returnValue(locale));
			}
		});
	}
}
