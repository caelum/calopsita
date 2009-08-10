package br.com.caelum.calopsita.infra.interceptor;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
public class LocaleInterceptor implements Interceptor {

	private final Result result;
	private final HttpServletRequest request;

	public LocaleInterceptor(Result result, HttpServletRequest request) {
		this.result = result;
		this.request = request;
	}

	public boolean accepts(ResourceMethod method) {
		return true;
	}

	public enum DateFormat {
		en("MM/dd/yyyy", "mm/dd/yy"),
		pt("dd/MM/yyyy", "dd/mm/yy");
		private String jodaFormat;
		private String jsFormat;

		private DateFormat(String jodaFormat, String jsFormat) {
			this.jodaFormat = jodaFormat;
			this.jsFormat = jsFormat;
		}
		public String getJodaFormat() {
			return jodaFormat;
		}
		public String getJsFormat() {
			return jsFormat;
		}

		public SimpleDateFormat getFormat() {
			return new SimpleDateFormat(getJodaFormat());
		}
		public static DateFormat valueFor(Locale locale) {
			try {
				return valueOf(locale.getLanguage());
			} catch (IllegalArgumentException e) {
				return en;
			}
		}
	}
	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object resourceInstance) throws InterceptionException {
		String language = request.getLocale().getLanguage();
		result.include("locale", language);
		result.include("format", DateFormat.valueFor(request.getLocale()));
		stack.next(method, resourceInstance);
	}

}
