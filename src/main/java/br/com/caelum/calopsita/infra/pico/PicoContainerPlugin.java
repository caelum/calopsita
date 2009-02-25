package br.com.caelum.calopsita.infra.pico;

import java.util.Map;

import org.apache.log4j.Logger;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.vraptor.VRaptorException;
import org.vraptor.component.ComponentInstantiationException;
import org.vraptor.config.ConfigException;
import org.vraptor.introspector.BeanProvider;
import org.vraptor.introspector.Introspector;
import org.vraptor.plugin.VRaptorPlugin;
import org.vraptor.reflection.ReflectionUtil;
import org.vraptor.scope.ApplicationContext;
import org.vraptor.webapp.WebApplication;

public class PicoContainerPlugin implements VRaptorPlugin {
    private static final Logger LOGGER = Logger.getLogger(PicoContainerPlugin.class);
    static final String APPLICATION_SCOPED_DEPENDENCIES_KEY = "org.vraptor.plugin.pico.DependenciesConfigMap";
    static final String APPLICATION_SCOPED_CONTAINER_KEY = "org.vraptor.plugin.pico.ApplicationContainer";
    private final Map<String, String> configuration;

    public PicoContainerPlugin(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    public void init(final WebApplication application) throws VRaptorException {
        setupApplicationContainer(application.getApplicationContext());
        setupBeanProvider(application.getIntrospector());

        LOGGER.info("PicoContainer Plugin is running.");
    }

    void setupApplicationContainer(final ApplicationContext applicationContext)
            throws ConfigException {
        final MutablePicoContainer applicationContainer = new DefaultPicoContainer();

        try {
            ContainerConfig config = instantiateInterface(getContainerConfigClass(),
                    ContainerConfig.class);
            config.configureApplicationContainer(applicationContainer);
            applicationContext.setAttribute(ContainerConfig.class.getName(), config);
        } catch (ComponentInstantiationException e) {
            throw new ConfigException(e);
        }

        applicationContext.setAttribute(APPLICATION_SCOPED_CONTAINER_KEY, applicationContainer);
    }

    private String getContainerConfigClass() throws ConfigException {
        String className = configuration.get("container-config-class");
        if (null == className)
            throw new ConfigException("Must set container-config-class property on vraptor.xml");
        return className;
    }

    private void setupBeanProvider(final Introspector introspector) {
        final BeanProvider defaultProvider = introspector.getBeanProvider();
        final PicoProvider newBeanProvider = new PicoProvider(defaultProvider);
        introspector.setBeanProvider(newBeanProvider);
    }

    public static <T> T instantiateInterface(String concreteClassName, Class<T> theInterface)
            throws ComponentInstantiationException {
        Object maybeInstance = ReflectionUtil.instantiate(concreteClassName);
        if (!(theInterface.isInstance(maybeInstance)))
            throw new ComponentInstantiationException(concreteClassName + " should implement "
                    + theInterface.getName());

        return theInterface.cast(maybeInstance);
    }
}
