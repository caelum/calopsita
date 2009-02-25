package br.com.caelum.calopsita.infra.di;

import java.lang.reflect.Type;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.picocontainer.PicoContainer;
import org.picocontainer.injectors.FactoryInjector;

public class SessionInjector extends FactoryInjector<Session> {

    @Override
    public Session getComponentInstance(PicoContainer pico, Type type) {
        return pico.getComponent(SessionFactory.class).getCurrentSession();
    }
    
    @Override
    public Object getComponentKey() {
    	return "org.hibernate.Session";
    }

}