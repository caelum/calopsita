package br.com.caelum.calopsita.infra.interceptor;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.caelum.calopsita.controller.HomeController;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
public class HibernateInterceptor implements Interceptor {

	private final Session session;

    public HibernateInterceptor(Session session) {
		this.session = session;
    }

	public boolean accepts(ResourceMethod method) {
		return !method.getMethod().getDeclaringClass().equals(HomeController.class);
	}

	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {

        Transaction transaction = session.beginTransaction();
        try {
            stack.next(method, resourceInstance);
            session.getTransaction().commit();
        } catch (Exception e) {
        	if (session.getTransaction().isActive()) {
				transaction.rollback();
			}
            throw new InterceptionException(e);
        }

	}

}
