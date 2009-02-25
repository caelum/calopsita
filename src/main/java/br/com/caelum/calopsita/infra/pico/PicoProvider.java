package br.com.caelum.calopsita.infra.pico;

import org.apache.log4j.Logger;
import org.picocontainer.PicoContainer;
import org.picocontainer.containers.EmptyPicoContainer;
import org.vraptor.LogicRequest;
import org.vraptor.introspector.BeanProvider;

public class PicoProvider implements BeanProvider {
    private static final Logger LOGGER = Logger.getLogger(PicoProvider.class);
    static final String REQUEST_SCOPED_CONTAINER_KEY = "requestContainer";
    private final BeanProvider parentProvider;

    public PicoProvider(final BeanProvider parentProvider) {
        this.parentProvider = parentProvider;
    }

    public Object findAttribute(final LogicRequest context, final String key) {
        PicoContainer container = getRequestContainer(context);

        Object bean = container.getComponent(key);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Resolved bean with key [" + key + "] to object [" + bean + "]");
        }

        return bean != null ? bean : parentProvider.findAttribute(context, key);
    }

    private PicoContainer getRequestContainer(final LogicRequest context) {
        Object containerInRequest = context.getApplicationContext().getAttribute(
                PicoContainerPlugin.APPLICATION_SCOPED_CONTAINER_KEY);

        if (containerInRequest == null) {
            LOGGER
                    .debug("PicoContainer not found in request scope, using default bean resolution. "
                            + "If this is unexpected, make sure the appropriate interceptors are in place.");
            return new EmptyPicoContainer();
        }

        return (PicoContainer) containerInRequest;
    }

}
