package br.com.caelum.calopsita.infra.vraptor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.Converter;
import br.com.caelum.vraptor.core.Converters;
import br.com.caelum.vraptor.core.DefaultConverters;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.Container;

@Component
@ApplicationScoped
public class CalopsitaConverters implements Converters {

	private final Converters delegate;
	private final List<Class<? extends Converter>> types;

	public CalopsitaConverters(Container container) {
		delegate = container.instanceFor(DefaultConverters.class);
		types = new ArrayList<Class<? extends Converter>>();
		types.add(LocalDateConverter.class);
	}
	@Override
	@PostConstruct
	public void init() {
	}

	@Override
	public Converter<?> to(Class<?> type, Container container) {
		for (Class<? extends Converter> converterType : types) {
            Class boundType = converterType.getAnnotation(Convert.class).value();
            if (boundType.isAssignableFrom(type)) {
                return container.instanceFor(converterType);
            }
        }
		return delegate.to(type, container);
	}

}
