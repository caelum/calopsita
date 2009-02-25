package br.com.caelum.calopsita.infra.vraptor.views;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A collection of RedirectTo
 * 
 * @author sergio
 * @version 0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Redirects {
	RedirectTo[] value();
}
