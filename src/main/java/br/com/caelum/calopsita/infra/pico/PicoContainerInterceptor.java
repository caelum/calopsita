package br.com.caelum.calopsita.infra.pico;

import org.apache.log4j.Logger;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;
import org.picocontainer.PicoContainer;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.LogicRequest;
import org.vraptor.annotations.In;
import org.vraptor.scope.ScopeType;
import org.vraptor.view.ViewException;

public class PicoContainerInterceptor implements Interceptor {
	private static final Logger LOGGER = Logger.getLogger(PicoContainerInterceptor.class);

	@In(scope = ScopeType.APPLICATION, key="org.vraptor.plugin.pico.ContainerConfig")
	private ContainerConfig containerConfig;

	@In(scope = ScopeType.APPLICATION, key=PicoContainerPlugin.APPLICATION_SCOPED_CONTAINER_KEY)
	private PicoContainer applicationContainer;

	public PicoContainerInterceptor() {
	}

	PicoContainerInterceptor(final ContainerConfig config, final PicoContainer applicationContainer) {
		this.containerConfig = config;
		this.applicationContainer = applicationContainer;
	}

	public void intercept(final LogicFlow flow) throws LogicException, ViewException {
		LOGGER.debug("Preparing request for PicoContainer injection");
		setupRequestContainer(flow.getLogicRequest());
		flow.execute();
	}

	MutablePicoContainer setupRequestContainer(final LogicRequest request) {
		final MutablePicoContainer requestContainer = new PicoBuilder(applicationContainer).withCaching().build(); 
		containerConfig.configureRequestContainer(requestContainer);

		request.getRequestContext().setAttribute(PicoProvider.REQUEST_SCOPED_CONTAINER_KEY, requestContainer);
		return requestContainer;
	}
}
