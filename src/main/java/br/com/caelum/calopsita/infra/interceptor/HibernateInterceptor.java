package br.com.caelum.calopsita.infra.interceptor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.view.ViewException;

public class HibernateInterceptor implements Interceptor {

    private final SessionFactory factory;

    public HibernateInterceptor(SessionFactory factory) {
        this.factory = factory;
    }

    public void intercept(LogicFlow logicFlow) throws LogicException, ViewException {

        Session session = factory.getCurrentSession();

        Transaction transaction = session.beginTransaction();
        try {
            logicFlow.execute();
            transaction.commit();
        } catch (Exception e) {
        	if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
            throw new LogicException(e);
        }
    }

}
