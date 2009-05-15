package br.com.caelum.calopsita.infra.vraptor;

import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.http.route.RoutesConfiguration;
import br.com.caelum.vraptor.http.route.Rules;
import br.com.caelum.vraptor.ioc.ApplicationScoped;

@ApplicationScoped
public class CustomRoutes implements RoutesConfiguration {

    @Override
    public void config(Router router) {
        new Rules(router) {
            public void routes() {
                routeFor("/").is(ClientsController.class).list();
                routeFor("/clients/random").is(ClientsController.class).random();
                // routeFor("/(*)").is(type(""), method(""));
            }
        };

        // ListOfRoutes routes = ...;
        // router.add(routes);
    }

}
