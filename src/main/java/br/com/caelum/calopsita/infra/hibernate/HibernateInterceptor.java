package br.com.caelum.calopsita.infra.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.view.ViewException;

/**
 * Controla ciclo de vida de sessões do hibernate e de transações.
 */
public class HibernateInterceptor implements Interceptor {

    private final HibernateUtil hibernateUtil;

    public HibernateInterceptor(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public void intercept(LogicFlow logicFlow) throws LogicException,
            ViewException {

        Session session = this.hibernateUtil.currentSession();

        if (session == null) {
            session = this.hibernateUtil.openSession();
        } else {
            logicFlow.execute();
            return;
        }
        Transaction transaction = session.beginTransaction();

        try {
            logicFlow.execute();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new LogicException(e);
        } finally {
            try {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            } finally {
                this.hibernateUtil.closeCurrentSession();
            }
        }

    }

}
