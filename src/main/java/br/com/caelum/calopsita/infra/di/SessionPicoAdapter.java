package br.com.caelum.calopsita.infra.di;

import java.lang.reflect.Type;

import org.hibernate.Session;
import org.picocontainer.PicoCompositionException;
import org.picocontainer.PicoContainer;
import org.picocontainer.adapters.AbstractAdapter;

import br.com.caelum.calopsita.infra.hibernate.HibernateUtil;

public class SessionPicoAdapter extends AbstractAdapter<Session> {

	private static final long serialVersionUID = 1L;

	public SessionPicoAdapter() {
		super(Session.class, Session.class);
	}
	
	public Session getComponentInstance(PicoContainer pico, Type type)
			throws PicoCompositionException {
		HibernateUtil hibernateUtil = pico.getComponent(HibernateUtil.class);
		return hibernateUtil.currentSession();
	}

	public String getDescriptor() {
		return getClass().getName();
	}

	public void verify(PicoContainer pico) throws PicoCompositionException {
	}

}
