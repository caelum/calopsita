package br.com.caelum.calopsita.infra.vraptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.joda.time.LocalDate;

import br.com.caelum.calopsita.infra.interceptor.LocaleInterceptor.DateFormat;
import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.Converter;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.RequestInfo;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.validator.ValidationMessage;

@Convert(LocalDate.class)
@Component
public class LocalDateConverter implements Converter<LocalDate> {

	private final RequestInfo request;
	private final Validator validator;
	public LocalDateConverter(RequestInfo request, Validator validator) {
		this.request = request;
		this.validator = validator;
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
			validator.add(new ValidationMessage("bad.date.format", "bad.date.format"));
			return null;
		}
	}

}
