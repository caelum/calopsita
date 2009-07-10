package br.com.caelum.calopsita.infra.interceptor;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import br.com.caelum.calopsita.controller.HomeController;
import br.com.caelum.calopsita.infra.di.ManagedSession;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
public class HibernateInterceptor implements Interceptor {

    private final SessionFactory factory;
	private final ManagedSession session;

    public HibernateInterceptor(SessionFactory factory, ManagedSession session) {
        this.factory = factory;
		this.session = session;
    }

	@Override
	public boolean accepts(ResourceMethod method) {
		return !method.getMethod().getDeclaringClass().equals(HomeController.class);
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {
		session.setSession(factory.openSession());

        Transaction transaction = session.beginTransaction();
        try {
            stack.next(method, resourceInstance);
            session.getTransaction().commit();
        } catch (Exception e) {
        	if (session.getTransaction().isActive()) {
				transaction.rollback();
			}
            throw new InterceptionException(e);
        } finally {
        	session.close();
        }

	}

}
