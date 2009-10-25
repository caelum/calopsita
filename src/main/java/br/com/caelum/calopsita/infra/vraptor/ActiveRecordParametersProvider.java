package br.com.caelum.calopsita.infra.vraptor;

import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.http.ParametersProvider;
import br.com.caelum.vraptor.http.iogi.IogiParametersProvider;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.validator.Message;

@Component
public class ActiveRecordParametersProvider implements ParametersProvider {

	private static final Logger logger = Logger.getLogger(ActiveRecordParametersProvider.class);

	private final ParametersProvider delegate;

	private final Injector injector;

	public ActiveRecordParametersProvider(IogiParametersProvider delegate, Injector injector) {
		this.injector = injector;
		logger.debug("using parameters provider: " + delegate.getClass());
		this.delegate = delegate;
	}

	public Object[] getParametersFor(ResourceMethod method, List<Message> errors, ResourceBundle bundle) {
		Object[] parameters = delegate.getParametersFor(method, errors, bundle);
		for (Object object : parameters) {
			if (object instanceof List<?>) {
				List<?> list = (List<?>) object;
				for (Object obj : list) {
					injector.injectDependencies(obj);
				}
			} else {
				injector.injectDependencies(object);
			}
		}
		return parameters;
	}


}
