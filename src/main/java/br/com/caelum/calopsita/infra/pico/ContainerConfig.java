package br.com.caelum.calopsita.infra.pico;

import org.picocontainer.MutablePicoContainer;

public interface ContainerConfig {

	public void configureApplicationContainer(MutablePicoContainer container);

	public void configureRequestContainer(MutablePicoContainer container);

}
