package br.com.caelum.calopsita.infra.interceptor;

import java.util.Locale;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Interceptor;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
public class LocaleInterceptor implements Interceptor {

	private final Result result;

	public LocaleInterceptor(Result result) {
		this.result = result;
	}
	@Override
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
	}
	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object resourceInstance) throws InterceptionException {
		String language = Locale.getDefault().getLanguage();
		result.include("locale", language);
		result.include("format", DateFormat.valueOf(language));
		stack.next(method, resourceInstance);
	}

}
