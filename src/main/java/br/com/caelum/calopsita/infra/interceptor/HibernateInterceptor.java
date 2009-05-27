package br.com.caelum.calopsita.infra.interceptor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Interceptor;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
public class HibernateInterceptor implements Interceptor {

    private final SessionFactory factory;

    public HibernateInterceptor(SessionFactory factory) {
        this.factory = factory;
    }

	@Override
	public boolean accepts(ResourceMethod method) {
		return method.getMethod().isAnnotationPresent(Post.class) || method.getMethod().isAnnotationPresent(Delete.class);
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {
		Session session = factory.getCurrentSession();

        Transaction transaction = session.beginTransaction();
        try {
            stack.next(method, resourceInstance);
            transaction.commit();
        } catch (Exception e) {
        	if (transaction != null) {
				transaction.rollback();
			}
            throw new InterceptionException(e);
        }

	}

}
