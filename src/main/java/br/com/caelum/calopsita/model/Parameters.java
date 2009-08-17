package br.com.caelum.calopsita.model;

public interface Parameters {

	boolean contains(String parameterName);

	<T> T get(String parameterName);

}
