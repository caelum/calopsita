package br.com.caelum.calopsita.infra.vraptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.joda.time.LocalDate;

import br.com.caelum.calopsita.infra.interceptor.LocaleInterceptor.DateFormat;
import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.Converter;
import br.com.caelum.vraptor.converter.ConversionError;
import br.com.caelum.vraptor.core.RequestInfo;
import br.com.caelum.vraptor.ioc.Component;

@Convert(LocalDate.class)
@Component
public class LocalDateConverter implements Converter<LocalDate> {

	private final RequestInfo request;
	public LocalDateConverter(RequestInfo request) {
		this.request = request;
	}

	public LocalDate convert(String value, Class<? extends LocalDate> type,
			ResourceBundle bundle) {
		if (value == null || value.equals("")) {
			return null;
		}
		Locale locale = request.getRequest().getLocale();
		SimpleDateFormat format = DateFormat.valueFor(locale).getFormat();
        try {
			return LocalDate.fromDateFields(format.parse(value));
		} catch (ParseException e) {
			throw new ConversionError("bad.date.format");
		}
	}

}
