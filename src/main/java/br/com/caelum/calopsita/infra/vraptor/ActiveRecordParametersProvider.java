package br.com.caelum.calopsita.infra.vraptor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ResourceBundle;

import net.vidageek.mirror.dsl.Matcher;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.matcher.SetterMatcher;

import org.picocontainer.annotations.Inject;

import br.com.caelum.vraptor.http.ParametersProvider;
import br.com.caelum.vraptor.http.ognl.OgnlParametersProvider;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.Container;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.validator.Message;

@Component
public class ActiveRecordParametersProvider implements ParametersProvider {

	private final OgnlParametersProvider delegate;
	private final Container container;

	public ActiveRecordParametersProvider(OgnlParametersProvider delegate, Container container) {
		this.delegate = delegate;
		this.container = container;
	}

	public Object[] getParametersFor(ResourceMethod method, List<Message> errors, ResourceBundle bundle) {
		Object[] parameters = delegate.getParametersFor(method, errors, bundle);
		Mirror mirror = new Mirror();
		for (Object object : parameters) {
			List<Method> methods = mirror.on(object.getClass()).reflectAll().methodsMatching(new InjectMatcher());
			for (Method toInject : methods) {
				Class<?> typeToInject = toInject.getParameterTypes()[0];
				Object instanceToInject = container.instanceFor(typeToInject);
				mirror.on(object).invoke().method(toInject).withArgs(instanceToInject);
			}
		}
		return parameters;
	}

	public static class InjectMatcher implements Matcher<Method> {
		private final SetterMatcher setter;
		public InjectMatcher() {
			setter = new SetterMatcher();
		}
		public boolean accepts(Method method) {
			return setter.accepts(method) && method.isAnnotationPresent(Inject.class);
		}
	}
}
