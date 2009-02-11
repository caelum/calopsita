package br.com.caelum.calopsita.infra.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.picocontainer.DefaultPicoContainer;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.annotations.Out;
import org.vraptor.component.ComponentType;
import org.vraptor.scope.RequestContext;
import org.vraptor.scope.ScopeType;
import org.vraptor.view.ViewException;

/**
 * Implementação ingênua de um interceptador que procura no pico as classes que
 * ele precisa para instanciar componentes.
 * 
 * E depois outjeta os fields anotados com @Out(APPLICATION) para o container do
 * pico.
 * 
 * @author sergio
 */
public class PicoInjectionInterceptor implements Interceptor {

	private static final Logger logger = Logger.getLogger(PicoInjectionInterceptor.class);

	private final DefaultPicoContainer pico;

	public PicoInjectionInterceptor(DefaultPicoContainer pico) {
		this.pico = pico;
	}

	@SuppressWarnings("unchecked")
	public void intercept(LogicFlow logicFlow) throws LogicException, ViewException {

		RequestContext requestContext = logicFlow.getLogicRequest().getRequestContext();
		ComponentType componentType = logicFlow.getLogicRequest().getLogicDefinition().getComponentType();

		Constructor construtor = componentType.getComponentClass().getConstructors()[0];
		for (Class paramClass : construtor.getParameterTypes()) {
			if (paramClass.equals(String.class)) {
				continue;
			}

			logger.trace("Procurando " + paramClass.getName() + " no pico");

			Object object = this.pico.getComponent(paramClass);
			if (object != null) {
				logger.debug("Injetando objeto da classe " + object.getClass().getName());
				requestContext.setAttribute(paramClass.getName(), object);
			}
		}

		logicFlow.execute();

		Object component = logicFlow.getLogicRequest().getLogicDefinition().getComponent();

		// registra no pico objetos com @Out de APPLICATION
		Field[] fields = componentType.getComponentClass().getDeclaredFields();
		for (Field field : fields) {

			if (field.isAnnotationPresent(Out.class)) {
				Out out = field.getAnnotation(Out.class);
				if (out.scope() == ScopeType.APPLICATION) {
					logger.trace("Achei @Out(APPLICATION)");

					Object value;
					try {
						field.setAccessible(true);
						value = field.get(component);
					} catch (Exception e) {
						logger.error(e);
						continue;
					}

					logger.trace("Registrando " + value + " como " + value.getClass().getName());
					this.pico.removeComponent(value.getClass());
					this.pico.addComponent(value);
				}
			}
		}

	}

}
