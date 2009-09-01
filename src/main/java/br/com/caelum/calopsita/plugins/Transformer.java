package br.com.caelum.calopsita.plugins;

import java.util.List;

import org.hibernate.Session;

public interface Transformer<T> {
	boolean accepts(Class<?> type);
	List<T> transform(List<T> list, Session session);
}
