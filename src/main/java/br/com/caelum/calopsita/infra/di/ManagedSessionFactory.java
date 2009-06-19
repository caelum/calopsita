package br.com.caelum.calopsita.infra.di;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;

import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

@Component
@ApplicationScoped
public class ManagedSessionFactory implements SessionFactory {

	private final SessionFactory factory;

	public ManagedSessionFactory() {
		this.factory = new AnnotationConfiguration().configure().buildSessionFactory();
	}

	public void close() throws HibernateException {
		factory.close();
	}

	public void evict(Class arg0, Serializable arg1) throws HibernateException {
		factory.evict(arg0, arg1);
	}

	public void evict(Class arg0) throws HibernateException {
		factory.evict(arg0);
	}

	public void evictCollection(String arg0, Serializable arg1)
			throws HibernateException {
		factory.evictCollection(arg0, arg1);
	}

	public void evictCollection(String arg0) throws HibernateException {
		factory.evictCollection(arg0);
	}

	public void evictEntity(String arg0, Serializable arg1)
			throws HibernateException {
		factory.evictEntity(arg0, arg1);
	}

	public void evictEntity(String arg0) throws HibernateException {
		factory.evictEntity(arg0);
	}

	public void evictQueries() throws HibernateException {
		factory.evictQueries();
	}

	public void evictQueries(String arg0) throws HibernateException {
		factory.evictQueries(arg0);
	}

	public Map getAllClassMetadata() throws HibernateException {
		return factory.getAllClassMetadata();
	}

	public Map getAllCollectionMetadata() throws HibernateException {
		return factory.getAllCollectionMetadata();
	}

	public ClassMetadata getClassMetadata(Class arg0) throws HibernateException {
		return factory.getClassMetadata(arg0);
	}

	public ClassMetadata getClassMetadata(String arg0)
			throws HibernateException {
		return factory.getClassMetadata(arg0);
	}

	public CollectionMetadata getCollectionMetadata(String arg0)
			throws HibernateException {
		return factory.getCollectionMetadata(arg0);
	}

	public Session getCurrentSession() throws HibernateException {
		return factory.getCurrentSession();
	}

	public Set getDefinedFilterNames() {
		return factory.getDefinedFilterNames();
	}

	public FilterDefinition getFilterDefinition(String arg0)
			throws HibernateException {
		return factory.getFilterDefinition(arg0);
	}

	public Reference getReference() throws NamingException {
		return factory.getReference();
	}

	public Statistics getStatistics() {
		return factory.getStatistics();
	}

	public boolean isClosed() {
		return factory.isClosed();
	}

	public Session openSession() throws HibernateException {
		return factory.openSession();
	}

	public Session openSession(Connection arg0, Interceptor arg1) {
		return factory.openSession(arg0, arg1);
	}

	public Session openSession(Connection arg0) {
		return factory.openSession(arg0);
	}

	public Session openSession(Interceptor arg0) throws HibernateException {
		return factory.openSession(arg0);
	}

	public StatelessSession openStatelessSession() {
		return factory.openStatelessSession();
	}

	public StatelessSession openStatelessSession(Connection arg0) {
		return factory.openStatelessSession(arg0);
	}


}
