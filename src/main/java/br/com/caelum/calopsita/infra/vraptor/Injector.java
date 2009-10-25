package br.com.caelum.calopsita.infra.vraptor;

import java.lang.reflect.Method;
import java.util.List;

import net.vidageek.mirror.dsl.Matcher;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.matcher.GetterMatcher;
import net.vidageek.mirror.matcher.SetterMatcher;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.Container;

@Component
public class Injector {

	private final Container container;
	public Injector(Container container) {
		this.container = container;
	}

	public void injectDependencies(Object object) {
		if (object != null) {
			Mirror mirror = new Mirror();
			List<Method> methods = mirror.on(object.getClass()).reflectAll().methodsMatching(new InjectMatcher());
			for (Method toInject : methods) {
				Class<?> typeToInject = toInject.getParameterTypes()[0];
				Object instanceToInject = container.instanceFor(typeToInject);
				mirror.on(object).invoke().method(toInject).withArgs(instanceToInject);
			}
			List<Method> recursive = mirror.on(object.getClass()).reflectAll().methodsMatching(new InjectRecursiveMatcher());
			for (Method method : recursive) {
				injectDependencies(mirror.on(object).invoke().method(method).withoutArgs());
			}
		}
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
	public static class InjectRecursiveMatcher implements Matcher<Method> {
		private final GetterMatcher getter;
		public InjectRecursiveMatcher() {
			getter = new GetterMatcher();
		}
		public boolean accepts(Method method) {
			return getter.accepts(method) && method.isAnnotationPresent(Inject.class);
		}
	}
}
