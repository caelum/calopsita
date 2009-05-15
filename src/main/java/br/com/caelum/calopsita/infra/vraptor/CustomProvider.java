package br.com.caelum.calopsita.infra.vraptor;

import br.com.caelum.vraptor.ComponentRegistry;
import br.com.caelum.vraptor.http.route.RoutesConfiguration;
import br.com.caelum.vraptor.ioc.pico.PicoProvider;

public class CustomProvider extends PicoProvider {

    @Override
    protected void registerComponents(ComponentRegistry container) {
        super.registerComponents(container);
        container.register(Repository.class, InMemoryDatabase.class);
        container.register(RoutesConfiguration.class, CustomRoutes.class);
    }
    
}
