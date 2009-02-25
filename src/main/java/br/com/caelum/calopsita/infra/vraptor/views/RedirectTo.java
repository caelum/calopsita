package br.com.caelum.calopsita.infra.vraptor.views;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Redirect VRaptor logic to some other resource.
 * Possible choices: another view, another logic, an URL or an arbitrary file.
 *
 * You need to define at least one of these possibilities or a runtime error will be thrown.
 *
 * Also needs logic method outcome.
 * 
 * @author sergio
 * @version 0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedirectTo {

	/**
	 * View to redirect to. Needs to be: "component.logic.result" or
	 * "logic.result" (logic in the same component) or only "result"
	 * (result in the same logic).
	 */
	String view() default "";
	
	/**
	 * Any file (html, JSP, etc) relative to context root.
	 */
	String file() default "";

	/**
	 * Some other logic. If it's in the same component, just type the
	 * logic name. If it's another component type "component.logic"
	 */
	String logic() default "";

	/**
	 * Client redirection to some other URL. All URLs should be relative
	 * to context root or external (with 'http' or 'https' prefix)
	 */
	String url() default "";

	/**
	 * Logic outcome 
	 */
	String when() default "ok";

}
