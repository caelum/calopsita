package br.com.caelum.calopsita.infra.vraptor;

import java.util.ArrayList;
import java.util.List;

import org.vraptor.component.ComponentType;
import org.vraptor.component.ComponentTypeWrapper;
import org.vraptor.interceptor.InterceptorType;

/**
 * ComponentWrapper que devolve mais interceptors
 * 
 * @author sergio
 */
public class GlobalInterceptorsComponentWrapper extends ComponentTypeWrapper {

    private final List<InterceptorType> interceptors = new ArrayList<InterceptorType>();

    public GlobalInterceptorsComponentWrapper(ComponentType component) {
        super(component);

        // interceptors.add(InterceptorType.getType(HibernateInterceptor.class));
        // interceptors.add(InterceptorType.getType(PicoInjectionInterceptor.class));
        interceptors.addAll(component.getInterceptors());
    }

    @Override
    public List<InterceptorType> getInterceptors() {
        return interceptors;
    }
}
